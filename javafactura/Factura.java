/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.time.LocalDate;
import java.time.LocalDate;
import java.time.LocalTime;
import java.io.Serializable;
import java.util.Optional;
import java.lang.Object;
/**
 *
 * @author Nos
 */
public class Factura implements Serializable

{
    /** NIF do contribuinte emitente */
    private int nif_emitente;

    /** Nome do contribuinte emitente */
    private String emitente;

    /** Data da despesa */
    private LocalDate data;

    /** NIF do contribuinte cliente */
    private int nif_cliente;

    /** Descricao da despesa */
    private String descricao;

    /** Valor da despesa */
    private double valor;

    /** Actividade da despesa */
    private Atividades atividade;

    /**
     * Construtor vazio
     */
    public Factura(){
        nif_emitente=0;
        emitente = new String();
        data = LocalDate.now();
        nif_cliente = 0;
        descricao = new String();
        valor = 0.0;
        atividade = new SemAtividade();
    }

    /**
     * Contrutor parametrico incluindo a atividade
     *
     * @param nife NIF do contribuinte emitente
     * @param emi Nome do contribuinte emitente
     * @param date Data da despesa
     * @param nifc NIF do contribuinte cliente
     * @param descr Descricao da despesa
     * @param valor Valor da despesa
     * @param ativ Actividade da despesa
     */
    public Factura(int nife, String emi, LocalDate date, int nifc, String descr, double valor, Atividades ativ){
        this.nif_emitente=nife;
        this.emitente = emi;
        this.data=date;
        this.nif_cliente=nifc;
        this.descricao = descr;
        this.valor = valor;
        this.atividade = ativ;
    }

    /**
     * Construtor de uma fatura sem atividade
     *
     * @param nife NIF do contribuinte emitente
     * @param emi Nome do contribuinte emitente
     * @param date Data da despesa
     * @param nifc NIF do contribuinte cliente
     * @param descr Descricao da despesa
     * @param valor Valor da despesa
     */
    public Factura(int nife, String emi, LocalDate date, int nifc, String descr, double valor){
        this.nif_emitente = nife;
        this.emitente = emi;
        this.data=date;
        this.nif_cliente=nifc;
        this.descricao = descr;
        this.valor = valor;
        this.atividade = new SemAtividade();
    }

    /**
     * Construtor de copia
     *
     * @param f A outra factura
     */
    public Factura(Factura f){
        this.nif_emitente=f.getNifEmitente();
        this.emitente=f.getEmitente();
        this.data=f.getData();
        this.nif_cliente=f.getNifCliente();
        this.descricao=f.getDescricao();
        this.valor = f.getValor();
        this.atividade = f.getAtividade();
    }

    /**
     * Devolve o NIF do contribuinte emitente da factura
     *
     * @return O NIF do contribuinte emitente da factura
     */
    public int getNifEmitente(){
        return this.nif_emitente;
    }

    /**
     * Devolve o nome do contribuinte emitente da factura
     *
     * @return O nome do contribuinte emitente da factura
     */
    public String getEmitente(){
        return new String(this.emitente);
    }

    /**
     * Devolve a data da factura
     *
     * @return A data da factura
     */
    public LocalDate getData(){
        return this.data;
    }

    /**
     * Devolve o NIF do cliente da factura
     *
     * @return O NIF do cliente da factura
     */
    public int getNifCliente(){
        return this.nif_cliente;
    }

    /**
     * Devolve a descricao da factura
     *
     * @return A descricao da factura
     */
    public String getDescricao(){
        return this.descricao;
    }

    /**
     * Devolve o valor da factura
     *
     * @return O valor da factura
     */
    public double getValor(){
        return this.valor;
    }

    /**
     * Devolve a actividade da factura
     *
     * @return A actividade da factura
     */
    public Atividades getAtividade(){
        return this.atividade;
    }

    /**
     * Altera a actividade da factura
     *
     * @param a A nova actividade
     */
    public void setActividade(Atividades a){
        this.atividade = a;
    }

    /**
     * Verifica se uma factura esta classificada ou nao
     *
     * @return true se a factura estiver classificada, senao false
     */
    public boolean classificada ()
    {
        return this.atividade.getClass().getSimpleName() != "SemAtividade";
    }

    public Factura clone(){
        return new Factura(this);
    }

    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || this.getClass() != o.getClass()) return false;

        Factura f = (Factura) o;
        return this.getData().equals(f.getData())
            && this.getDescricao().equals(f.getDescricao())
            && this.getEmitente().equals(f.getEmitente())
            && this.getNifCliente() == f.getNifCliente()
            && this.getNifEmitente() == f.getNifEmitente();
    }

    public String toString(){
        StringBuilder sb =  new StringBuilder("Factura [");
        sb.append("Nif do Emitente: ").append(nif_emitente+", ");
        sb.append("Nome Emitente: ").append(emitente+", ");
        sb.append("Data: ").append(data.toString()+", ");
        sb.append("Nif do Cliente: ").append(nif_cliente+", ");
        sb.append("Descricao: ").append(descricao+", ");
        sb.append("Valor total: ").append(valor+", ");
        sb.append("Atividade: ").append(atividade.getClass().getSimpleName()+"]");

        return sb.toString();
    }


    /**
     * Metodo que devovlve o valor da deducao de uma Factura, partindo do principio que esta fatura ja tem uma atividade associada
     */
    public double deducao (double fc, double fe)
    {
        return this.getAtividade()
            .calculadeducao(this.getValor(), fc, fe);
    }

    public int hashCode ()
    {
        long l = Double.doubleToLongBits(this.getValor());
        return this.getNifEmitente()
            ^ this.getEmitente().hashCode()
            ^ this.getData().hashCode()
            ^ this.getNifCliente()
            ^ this.getDescricao().hashCode()
            ^ ((int) (l ^ (l >>> 32)))
            ^ this.getAtividade().hashCode();
    }
}
