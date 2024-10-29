package uz.smartbank.business.esb.api.anor;

public class AnorService {

    public String echo(String message) {
        return message == null ? "Hello From Anor" : "Hello " + message;
    }
}
