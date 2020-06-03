import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.io.Serializable;

public class ContribuinteFamiliaNumerosa
    extends ContribuinteIndividual
    implements ReducaoImposto, Serializable
{
    /**
     * Construtor vazio
     */
    public ContribuinteFamiliaNumerosa ()
    {
        super();
    }

    /**
     * Construtor vazio
     */
    public ContribuinteFamiliaNumerosa (ContribuinteFamiliaNumerosa c)
    {
        super(c);
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
    public ContribuinteFamiliaNumerosa (int n, String mail, String name, String mor, String pass, Set<Integer> nfiscais)
    {
        super(n, mail, name, mor, pass, nfiscais);
    }

    public double getCoeficiente ()
    {
        return this.reducaoImposto();
    }

    public double reducaoImposto ()
    {
        return super.getCoeficiente() + (this.getNAgregado() * 0.05);
    }

    public ContribuinteFamiliaNumerosa clone ()
    {
        return new ContribuinteFamiliaNumerosa(this);
    }
}
