package chemquizAPI.common;

public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    @Override
    public String toString() {
        return this.success + " " + this.message + " " + this.data;
    }

    // without these getters, was not able to return ApiResponse after an http request!?
    public boolean getSuccess() {
        return this.success;
    }

    public String getMessage() {
        return this.message;
    }

    public T getData() {
        return this.data;
    }
}
