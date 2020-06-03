import java.io.Serializable;

/**
 * Actividade economica
 *
 * @author a76257, a76361, a78164
 * @version 20180523
 */
public abstract class Atividades implements Serializable


{
    /** Codigo único da actividade */
    private int codigo;

    /** Percentagem da deduçao total da despesa da actividade */
    private double fator;

    /**
     * Construtor parametrizado
     *
     * @param cod Codigo unico para a actividade
     * @param fat Percentagem da deduçao total da despesa da actividade
     */
    public Atividades(int cod, double fat)
    {
        this.codigo=cod;
        this.fator=fat;
    }

    /**
     * Construtor de copia
     *
     * @param ativ Uma instancia da classe Atividades
     */
    public Atividades(Atividades ativ)
    {
        this.codigo=getCodigo();
        this.fator=getFator();
    }

    /**
     * @return Percentagem da deduçao total da despesa da actividade
     */
    public double getFator()
    {
        return this.fator;
    }

    /**
     * @return Codigo da actividade
     */
    public int getCodigo()
    {
        return this.codigo;
    }
    
    /**
     * Calcula o valor de deducao
     *
     * @param valor Valor da despesa
     * @param fc Factor do cliente
     * @param fe Factor da empresa
     * @return Valor de deducao
     */

    public abstract double calculadeducao(double valor, double fc, double fe);


    public abstract Atividades clone();

    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || this.getClass() != o.getClass()) return false;

        Atividades f = (Atividades) o;
        return this.codigo == f.getCodigo();
    }

    public int hashCode ()
    {
        return this.codigo;
    }
}
