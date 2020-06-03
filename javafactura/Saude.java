import java.io.Serializable;
/**
 * Escreva a descrição da classe Saude aqui.
 *
 * @author (seu nome)
 * @version (número de versão ou data)
 */
public class Saude extends Atividades implements Serializable
{
    public Saude()
    {
        super(1, 0.5);
    }

    public Saude(Saude t)
    {
        super(t);
    }

    public Atividades clone()
    {
        return new Saude(this);
    }


    public double calculadeducao(double valor, double fc, double fe)
    {
        if (valor > 1000)
            valor = 1000;
      return valor * fc * fe;
    }

    public String toString(){
        StringBuilder sb =  new StringBuilder("Saude");
        return sb.toString();
    }
}
