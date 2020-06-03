import java.io.Serializable;

/**
 * Escreva a descrição da classe Restauracao aqui.
 *
 * @author (seu nome)
 * @version (número de versão ou data)
 */
public class Restauracao extends Atividades implements Serializable
{
    public Restauracao()
    {
        super(4, 0);
    }

    public Restauracao(Restauracao t)
    {
        super(t);
    }

    public Atividades clone()
    {
        return new Restauracao(this);
    }


    public double calculadeducao(double valor,double fc, double fe)
    {
        if(valor>751) valor=751;
        return valor * fc * fe;
    }
    
    public String toString(){
        StringBuilder sb =  new StringBuilder("Restauracao");
        return sb.toString();
    }
}
