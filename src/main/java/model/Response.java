package model;

/**
 * Used to show the response result of an operation.
 */
public class Response {

    /**
     * The status of the current operation (success: 200, filure: 400).
     **/
    private int status;

    /**
     * The response message.
     **/
    private String message;

    /**
     * Constructor for the Response class with response status and information.
     *
     * @param status the response status
     * @param info   the response information
     */
    public Response(int status, String info) {
        this.status = status;
        this.message = info;
    }

    /**
     * Get the response status.
     *
     * @return the reponse status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Get the response info.
     *
     * @return the response info
     */
    public String getMessage() {
        return message;
    }

    /**
     * Construct a success response.
     *
     * @param info the successful information
     * @return a Response object
     */
    public static Response success(String info) {
        return new Response(200, info);
    }

    /**
     * Construct a failure response.
     *
     * @param info the failed information
     * @return a Response object
     */
    public static Response failure(String info) {
        return new Response(400, info);
    }
}
