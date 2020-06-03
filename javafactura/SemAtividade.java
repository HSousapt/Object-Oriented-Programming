
/**
 * Class SemAtividade
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class SemAtividade extends Atividades
{
    public SemAtividade()
    {
        super(-1, 0);
    }

    public SemAtividade(SemAtividade t)
    {
        super(t);
    }

    public Atividades clone()
    {
        return new SemAtividade(this);
    }

    public double calculadeducao(double valor, double fc, double fe)
    {
        return 0;
    }
}
