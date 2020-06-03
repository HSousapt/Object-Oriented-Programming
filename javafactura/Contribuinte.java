/**
 *
 * @author eu
 */

import java.io.Serializable;

public abstract class Contribuinte implements Serializable {
    /** O NIF do contribuinte */
    private int nif;

    /** O email do contribuinte */
    private String email;

    /** O nome do contribuinte */
    private String nome;

    /** A morada do contribuinte */
    private String morada;

    /** A password do contribuinte */
    private String password;

    /**
     * Construtor vazio
     */
    public Contribuinte()
    {
        this.nif = 0;
        this.email = "n/d";
        this.nome = "n/d";
        this.morada = "n/d";
        this.password = "n/d";
    }

    /**
     * Construtor parametrizado
     *
     * @param n NIF do contribuinte
     * @param mail Mail do contribuinte
     * @param name Nome do contribuinte
     * @param mor Morada do contribuinte
     * @param pass Password do contribuinte
     */
    public Contribuinte(int n, String mail, String name, String mor, String pass)
    {
        this.nif = n;
        this.email = mail;
        this.nome = name;
        this.morada = mor;
        this.password = pass;
    }

    /**
     * Construtor de copia
     *
     * @param c O outro contribuinte
     */
    public Contribuinte(Contribuinte c)
    {
        this.nif = c.getNif();
        this.email = c.getEmail();
        this.nome = c.getNome();
        this.morada = c.getMorada();
        this.password = c.getPass();
    }

    /**
     * Devolve o NIF do contribuinte
     *
     * @return O NIF do contribuinte
     */
    public int getNif()
    {
        return this.nif;
    }

    /**
     * Devolve o mail do contribuinte
     *
     * @return O mail do contribuinte
     */
    public String getEmail()
    {
        return this.email;
    }

    /**
     * Devolve o nome do contribuinte
     *
     * @return O nome do contribuinte
     */
    public String getNome()
    {
        return this.nome;
    }

    /**
     * Devolve a morada do contribuinte
     *
     * @return A morada do contribuinte
     */
    public String getMorada()
    {
        return this.morada;
    }

    /**
     * Devolve a password do contribuinte
     *
     * @return A password do contribuinte
     */
    public String getPass()
    {
        return this.password;
    }

    /**
     * Altera o mail do contribuinte
     *
     * @param mail O novo mail do contribuinte
     */
    public void setEmail(String mail)
    {
        this.email = mail;
    }

    /**
     * Altera a morada do contribuinte
     *
     * @param morada A nova morada do contribuinte
     */
    public void setMorada(String morada)
    {
        this.morada = morada;
    }

    /**
     * Altera a password do contribuinte
     *
     * @param pass A nova password do contribuinte
     */
    public void setPass(String pass)
    {
        this.password = pass;
    }


    public abstract Contribuinte clone();
    public abstract double getCoeficiente ();

    public boolean equals(Object obj)
    {
        return obj != null
            && this.getClass() == obj.getClass()
            && ((Contribuinte) obj).getNif() == this.getNif();

    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("NIF: ").append(nif).append(", ")
            .append("Nome: ").append(nome).append(", ")
            .append("Mail: ").append(email).append(", ")
            .append("Morada: ").append(morada).append(", ");

        return sb.toString();
    }

    public int hash()
    {
        return this.nif;
    }
}
