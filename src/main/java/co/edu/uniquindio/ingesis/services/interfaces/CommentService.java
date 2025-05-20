package co.edu.uniquindio.ingesis.services.interfaces;

import co.edu.uniquindio.ingesis.dtos.CommentRequest;
import co.edu.uniquindio.ingesis.dtos.CommentResponse;
import co.edu.uniquindio.ingesis.dtos.PaginationRequest;

import java.util.List;

public interface CommentService {

    CommentResponse createComment(CommentRequest request);

    CommentResponse getComment(Long id);

    String deleteComment(Long id);

    CommentResponse updateComment(Long id, CommentRequest request);

    List<CommentResponse> listComments(PaginationRequest paginationRequest, Long programId);
}
