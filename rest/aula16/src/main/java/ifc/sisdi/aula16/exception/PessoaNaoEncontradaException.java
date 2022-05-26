package ifc.sisdi.aula16.exception;

public class PessoaNaoEncontradaException extends RuntimeException{
    public PessoaNaoEncontradaException(int id) {
        super("Não foi possível encontrar pessoa com o id: " + id);
    }
}