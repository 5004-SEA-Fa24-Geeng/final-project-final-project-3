package model;

/**
 * Used to show the response result of an operation.
 */
public class Response {

    /** The status of the current operation (success: 200, filure: 400). **/
    private int status;

    /** The response information. **/
    private String info;

    /** Used to check if the button should be disabled. **/
    private boolean isDisabled;

    /**
     * Constructor for the Response class with response status and information.
     * @param status the response status
     * @param info the response information
     */
    public Response(int status, String info) {
        this.status = status;
        this.info = info;
    }

    /**
     * Constructor for the Response class with response status and the status of button.
     * @param status the response status
     * @param isDisabled if the button should be disabled
     */
    public Response(int status, boolean isDisabled) {
        this.status = status;
        this.isDisabled = isDisabled;
    }

    /**
     * Get the response status.
     * @return the reponse status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Get the response info.
     * @return the response info
     */
    public String getInfo() {
        return info;
    }

    /**
     * Get the button status.
     * @return the status of button
     */
    public boolean isDisabled() {
        return isDisabled;
    }

    /**
     * Construct a success response.
     * @param info the successful information
     * @return a Response object
     */
    public static Response success(String info) {
        return new Response(200, info);
    }

    /**
     * Construct a failure response.
     * @param info the failed information
     * @return a Response object
     */
    public static Response failure(String info) {
        return new Response(400, info);
    }

    /**
     * Construct a response about button status.
     * @return a Response object.
     */
    public static Response disabled() {
        return new Response(400, true);
    }
}
