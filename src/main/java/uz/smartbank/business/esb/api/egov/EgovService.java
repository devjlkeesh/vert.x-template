package uz.smartbank.business.esb.api.egov;

public class EgovService {

    public String echo(String message) {
        return message == null ? "Hello From Egov" : "Hello " + message;
    }
}
