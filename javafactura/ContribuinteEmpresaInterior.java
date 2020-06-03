import java.util.Set;
import java.io.Serializable;

public class ContribuinteEmpresaInterior
    extends ContribuinteEmpresa
    implements ReducaoImposto, Serializable

{
    public ContribuinteEmpresaInterior ()
    {
        super();
    }

    public ContribuinteEmpresaInterior (ContribuinteEmpresaInterior c)
    {
        super(c);
    }

    public ContribuinteEmpresaInterior (Set<Atividades> atividades, int n, String mail, String name, String mor, String pass)
    {
        super(atividades, n, mail, name, mor, pass);
    }

    public double getCoeficiente ()
    {
        return this.reducaoImposto();
    }

    public double reducaoImposto ()
    {
        return super.getCoeficiente() + 0.1;
    }

    public ContribuinteEmpresaInterior clone ()
    {
        return new ContribuinteEmpresaInterior(this);
    }
}
