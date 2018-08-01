package pe.com.hatunsol.ferreterias.entity;

/**
 * Created by Administrator on 07/03/2015.
 */
public class RestResult {
    private String result;
    private int StatusCode;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int statusCode) {
        StatusCode = statusCode;
    }
}
