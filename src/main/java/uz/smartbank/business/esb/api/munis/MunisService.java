package uz.smartbank.business.esb.api.munis;

public class MunisService {

    public String echo(String message) {
        return message == null ? "Hello From Munis" : "Hello " + message;
    }
}
