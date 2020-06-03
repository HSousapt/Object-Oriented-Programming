/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.HashSet;
import java.util.Set;
import java.io.Serializable;

/**
 * @author eu
 */
public class ContribuinteEmpresa extends Contribuinte implements Serializable
{
    /** Actividades nas quais actua a empresa */
    private Set<Atividades> atividades;

    /**
     * Construtor vazio
     */
    public ContribuinteEmpresa ()
    {
        super();
        this.atividades = new HashSet();
    }

    /**
     * Construtor parametrizado
     *
     * @param atividades Actividades nas quais a empresa actua
     * @param n O NIF da empresa
     * @param mail O mail da empresa
     * @param name Nome da empresa
     * @param mor Morada da empresa
     * @param pass Password da conta
     */
    public ContribuinteEmpresa (Set<Atividades> atividades, int n, String mail,String name, String mor, String pass)
    {
        super(n, mail, name, mor, pass);
        this.atividades = new HashSet(atividades);
    }

    /**
     * Construtor por copia
     *
     * @param c A outra empresa
     */
    public ContribuinteEmpresa (ContribuinteEmpresa c)
    {
        super(c);
        this.atividades = c.getAtividades();
    }

    /**
     * Devolve as actividades em que a empresa actua
     *
     * @return As actividades em que a empresa actua
     */
    public Set<Atividades> getAtividades ()
    {
        return new HashSet(this.atividades);
    }

    public double getCoeficiente ()
    {
        return 0.3;
    }

    /**
     * Metodo clone da classe ContribuinteEmpresa
     */
    public ContribuinteEmpresa clone(){
        return new ContribuinteEmpresa(this);
    }

    /**
     * 
     */
     public String toString(){
        StringBuilder sb =  new StringBuilder("Empresa [");
        sb.append(super.toString());
        sb.append("[ Atividade(s): ");
        for(Atividades a: this.atividades){

            sb.append(a.toString()+", ");
        }
        sb.append("]; ");
        if(this.getClass().getSimpleName().equals("ContribuinteEmpresa")) 
            sb.append("Concelho: Outros "); 
        else 
            sb.append("Concelho: Concelho Interior"); 
        
        sb.append("]");
        
        return sb.toString();
    }
}
