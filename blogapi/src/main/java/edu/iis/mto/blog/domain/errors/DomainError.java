package edu.iis.mto.blog.domain.errors;

public class DomainError extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public static final String USER_NOT_FOUND = "unknown user";
    public static final String POST_NOT_FOUND = "unknown post";
    public static final String SELF_LIKE = "cannot like own post";
    public static final String USER_WRONG_STATUS = "post can be only by confirmed users";

    public DomainError(String msg) {
        super(msg);
    }

}
