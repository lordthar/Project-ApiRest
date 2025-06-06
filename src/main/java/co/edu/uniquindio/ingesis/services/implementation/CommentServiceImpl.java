package co.edu.uniquindio.ingesis.services.implementation;

import co.edu.uniquindio.ingesis.domain.Comment;
import co.edu.uniquindio.ingesis.domain.Program;
import co.edu.uniquindio.ingesis.domain.Student;
import co.edu.uniquindio.ingesis.dtos.CommentRequest;
import co.edu.uniquindio.ingesis.dtos.CommentResponse;
import co.edu.uniquindio.ingesis.dtos.PaginationRequest;
import co.edu.uniquindio.ingesis.exceptions.ResourceNotFoundException;
import co.edu.uniquindio.ingesis.mappers.CommentMapper;
import co.edu.uniquindio.ingesis.repositories.CommentRepository;
import co.edu.uniquindio.ingesis.repositories.ProgramRepository;
import co.edu.uniquindio.ingesis.services.interfaces.CommentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Inject
    MqttService mqttService;
    private final CommentRepository commentRepository;
    private final ProgramRepository programRepository;
    private final CommentMapper commentMapper;
    ObjectMapper objectMapper = new ObjectMapper();


    @Override
    @Transactional
    public CommentResponse createComment(CommentRequest request) {
        logger.info("Creando comentario: {}", request);

        Program program = programRepository.findById(request.programId());
        if (program == null) {
            throw new ResourceNotFoundException("Programa no encontrado con ID: " + request.programId());
        }

        Comment comment = new Comment();
        comment.setProgram(program);
        comment.setContent(request.content());
        comment.setCreatedAt(LocalDateTime.now());
        comment.persist();

        Student estudiante = program.getStudent();
        if (estudiante != null) {
            String mensaje = "Hola " + estudiante.getName() + ",\n\n" +
                    "Un profesor ha comentado tu programa \"" + program.getTitle() + "\" (código: " + program.getCode() + ").\n\n" +
                    "Comentario: " + request.content() + "\n\n" +
                    "Saludos";

            try {
                objectMapper.registerModule(new JavaTimeModule());
                objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

                ObjectNode payload = objectMapper.createObjectNode();
                payload.put("to", estudiante.getEmail());
                payload.put("subject", "Nuevo comentario en tu programa");
                payload.put("message", mensaje);

                String jsonPayload = objectMapper.writeValueAsString(payload);

                mqttService.publicar("comment/notify", jsonPayload);
                logger.info("Notificación MQTT enviada al estudiante: {}", estudiante.getName());

            } catch (JsonProcessingException e) {
                logger.error("Error al serializar el mensaje MQTT de comentario", e);
            }

        } else {
            logger.warn("El programa no tiene un estudiante asignado");
        }

        logger.info("Comentario creado con ID: {}", comment.id);
        return commentMapper.toCommentResponse(comment);
    }


    @Override
    public CommentResponse getComment(Long id) {
        logger.info("Obteniendo comentario con ID: {}", id);
        Comment comment = commentRepository.findById(id);
        if (comment == null) {
            throw new ResourceNotFoundException("Comentario no encontrado con ID: " + id);
        }
        return commentMapper.toCommentResponse(comment);
    }

    @Override
    @Transactional
    public String deleteComment(Long id) {
        logger.info("Eliminando comentario con ID: {}", id);
        boolean deleted = commentRepository.deleteById(id);
        if (!deleted) {
            throw new ResourceNotFoundException("Comentario no encontrado con ID: " + id);
        }
        logger.info("Comentario eliminado con éxito");
        return "Comentario eliminado con éxito";
    }

    @Override
    @Transactional
    public CommentResponse updateComment(Long id, CommentRequest request) {
        logger.info("Actualizando comentario con ID: {}", id);
        Comment comment = commentRepository.findById(id);
        if (comment == null) {
            throw new ResourceNotFoundException("Comentario no encontrado con ID: " + id);
        }

        comment.setContent(request.content());
        comment.setUpdatedAt(LocalDateTime.now());
        comment.persist();

        logger.info("Comentario actualizado con éxito");
        return commentMapper.toCommentResponse(comment);
    }

    @Override
    public List<CommentResponse> listComments(PaginationRequest paginationRequest, Long programId) {
        logger.info("Listando comentarios del programa {} con offset {} y limit {}", programId, paginationRequest.offset(), paginationRequest.limit());

        PanacheQuery<Comment> query = commentRepository.find("program.id", programId);
        query.page(paginationRequest.offset() / paginationRequest.limit(), paginationRequest.limit());

        List<Comment> comments = query.list();
        return commentMapper.toCommentResponseList(comments);
    }
}
