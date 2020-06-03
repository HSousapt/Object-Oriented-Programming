import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.io.Serializable;
/**
 *
 * @author eu
 */
public class ContribuinteIndividual extends Contribuinte implements Serializable
{
    /** NIFs dos agregados */
    private Set<Integer> nfiscais;

    /** Coeficiente fiscal */
    private final double coef = 0.2;

    /**
     * Construtor vazio
     */
    public ContribuinteIndividual ()
    {
        super();
        this.nfiscais = new TreeSet();
    }

    /**
     * Construtor parametrizado
     *
     * @param n NIF do contribuinte
     * @param mail Mail do contribuinte
     * @param name Nome do contribuinte
     * @param mor Morada do contribuinte
     * @param pass Password do contribuinte
     * @param nfiscais NIFs do agregado familiar
     */
    public ContribuinteIndividual (int n, String mail, String name, String mor, String pass, Set<Integer> nfiscais )
    {
        super(n, mail, name, mor, pass);
        this.nfiscais = new TreeSet(nfiscais);
    }

    /**
     * Construtor de copia
     *
     * @param c O outro contribuinte
     */
    public ContribuinteIndividual (ContribuinteIndividual c)
    {
        super(c);
        this.nfiscais = c.getNFiscais();
    }

    /**
     * Devolve o numero do agregado
     *
     * @return O numero do agregado
     */
    public int getNAgregado ()
    {
        return this.nfiscais.size() + 1;
    }

    /**
     * Devolve os NIFs do agregado
     *
     * @return NIFs do agregado
     */
    public Set<Integer> getNFiscais ()
    {
        return new TreeSet(this.nfiscais);
    }

    /**
     * Devolve o coeficiente fiscal
     *
     * @return O coeficiente fiscal
     */
    public double getCoeficiente ()
    {
        return this.coef;
    }

    /**
     * Metodo clone da classe ContribuinteIndividual
     */
    public ContribuinteIndividual clone(){
        return new ContribuinteIndividual(this);
    }

    public String toString(){
        StringBuilder sb =  new StringBuilder("Contribuinte [");
        sb.append(super.toString());
        sb.append("[ Agregado Familiar: ");
        for(Integer a: this.nfiscais){
            sb.append(a.toString()+", ");
        }
        sb.append("]; ");

        sb.append("]");
        
        return sb.toString();
    }
}
