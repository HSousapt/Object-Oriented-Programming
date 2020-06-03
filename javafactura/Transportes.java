import java.io.Serializable;

/**
 * Escreva a descrição da classe Transportes aqui.
 *
 * @author (seu nome)
 * @version (número de versão ou data)
 */
public class Transportes extends Atividades implements Serializable
{
    public Transportes()
    {
        super(0, 0.6);
    }

    public Transportes(Transportes t)
    {
        super(t);
    }

    public Atividades clone()
    {
        return new Transportes(this);
    }

    public double calculadeducao (double valor,double fc, double fe)
    {
        if(valor>193) valor=193;
        return valor * fc * fe;
    }
    
    public String toString(){
        StringBuilder sb =  new StringBuilder("Transporte");
        return sb.toString();
    }
}
