import java.io.Serializable;

/**
 * Escreva a descrição da classe Educacao aqui.
 *
 * @author (seu nome)
 * @version (número de versão ou data)
 */
public class Educacao extends Atividades implements Serializable
{
    public Educacao()
    {
        super(3, 0.7);
    }

    public Educacao(Educacao t)
    {
        super(t);
    }

    public Atividades clone()
    {
        return new Educacao(this);
    }


    public double calculadeducao (double valor, double fc, double fe)
    {
        if(valor>293) valor = 293;
        return valor * fc * fe;
    }
    
    public String toString(){
        StringBuilder sb =  new StringBuilder("Educacao");
        return sb.toString();
    }
}
