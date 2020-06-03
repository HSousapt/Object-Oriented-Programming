import java.io.Serializable;
/**
 * Escreva a descrição da classe Entretenimento aqui.
 *
 * @author (seu nome)
 * @version (número de versão ou data)
 */
public class Entretenimento extends Atividades implements Serializable
{
    public Entretenimento()
    {
        super(2, 0);
    }
    public Entretenimento(Entretenimento t)
    {
        super(t);
    }

    public Atividades clone()
    {
        return new Entretenimento(this);
    }

    public double calculadeducao (double valor, double fc, double fe)
    {
        return 0;
    }
    
    public String toString(){
        StringBuilder sb =  new StringBuilder("Entretenimento");
        return sb.toString();
    }
}
