import java.util.HashMap;
import java.util.TreeMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.time.LocalDate;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.stream.Stream;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import java.io.Serializable;

/**
 * Base de dados do programa JavaFactura
 *
 * @author a76257, a76361, a78164
 * @version 20180523
 */
public class BD implements Serializable
{
    /** Os contribuintes empresa */
    private Map<Integer, ContribuinteEmpresa> empresas;

    /** Os contribuintes individuais */
    private Map<Integer, ContribuinteIndividual> individuais;

    /** As facturas */
    private Map<Integer, Set<Factura>> facturas;

    /** Faturas Corrigidas(sofreram alteracoes na atividade)*/
    private List<Factura> facturaCorrigida;
    /**
     * Construtor vazio
     */
    public BD ()
    {
        this.empresas    = new HashMap<Integer, ContribuinteEmpresa>();
        this.facturas    = new HashMap<Integer, Set<Factura>>();
        this.individuais = new HashMap<Integer, ContribuinteIndividual>();
        this.facturaCorrigida = new ArrayList<Factura>();

    }

    /**
     * Construtor parametrizado
     *
     * @param empresas Os contribuintes empresa
     * @param individuais Os contribuintes individuais
     * @param facturas As facturas
     */
    public BD (Map<Integer, ContribuinteEmpresa> empresas, Map<Integer, ContribuinteIndividual> individuais, Map<Integer, Set<Factura>> facturas, List<Factura> facturaCorrigida)
    {
        this.empresas    = new HashMap<Integer, ContribuinteEmpresa>(empresas);
        this.facturas    = new HashMap<Integer, Set<Factura>>(facturas);
        this.individuais = new HashMap<Integer, ContribuinteIndividual>(individuais);
        this.facturaCorrigida = new ArrayList<Factura>(facturaCorrigida);

    }

    /**
     * Construtor por copia
     *
     * @param o Outra BD
     */
    public BD (BD o)
    {
        this.empresas    = new HashMap<Integer, ContribuinteEmpresa>(o.getEmpresas());
        this.facturas    = new HashMap<Integer, Set<Factura>>(o.getFacturas());
        this.individuais = new HashMap<Integer, ContribuinteIndividual>(o.getIndividuais());
        this.facturaCorrigida = new ArrayList<Factura>(o.getFacturaCorrigida());

    }

    /**
     * Devolve as facturas corrigidas 
     *
     * @return As facturas corrigidas 
     */
    public List<Factura> getFacturaCorrigida ()
    {
        return this.facturaCorrigida
            .stream()
            .map(Factura :: clone)
            .collect(Collectors.toList());
    }


    /**
     * Devolve as facturas classificadas de um determinado nifCliente
     *
     * @return As facturas classificadas de um determinado nifCliente
     */
    public Set<Factura> getClassificadas (int nifCliente)
    {
        return this.getFacturasIndividual(nifCliente)
            .stream()
            .filter(f -> f.classificada())
            .collect(Collectors.toSet());
    }

    /**
     * Devolve as facturas todas
     *
     * @return As facturas todas
     */
    public Map<Integer, Set<Factura>> getFacturas ()
    {
        return new HashMap<Integer, Set<Factura>>(this.facturas);
    }

    /**
     * Devolve as facturas todas
     *
     * @return As facturas todas
     */
    public Set<Factura> getTodasFacturas ()
    {
        Set<Factura> ret = new HashSet();

        for (Set<Factura> fs : this.getFacturas().values())
            ret.addAll(fs);

        return ret.stream()
            .map(Factura::clone)
            .collect(Collectors.toSet());
    }

    /**
     * Devolve os contribuintes empresa
     *
     * @return Os contribuintes empresa
     */
    public Map<Integer, ContribuinteEmpresa> getEmpresas ()
    {
        return new HashMap<Integer, ContribuinteEmpresa>(this.empresas);
    }

    /**
     * Devolve os contribuintes individuais
     *
     * @return Os contribuintes individuais
     */
    public Map<Integer, ContribuinteIndividual> getIndividuais ()
    {
        return new HashMap<Integer, ContribuinteIndividual>(this.individuais);
    }

    /**
     * Adiciona facturas corrigidas
     *
     */
    public void addFacturaCorrigida (Factura f)
    {
        this.addFactura(f);
        this.facturaCorrigida.add(f.clone());
    }

    /**
     * Adiciona um contribuinte individual a base de dados
     *
     * @param c O contribuinte a adicionar
     */
    public void addContribuinteIndividual (ContribuinteIndividual c)
    {
        this.individuais.put(c.getNif(), c.clone());
    }

    /**
     * Adiciona um contribuinte empresa a base de dados
     *
     * @param c O contribuinte a adicionar
     */
    public void addContribuinteEmpresa (ContribuinteEmpresa c)
    {
        this.empresas.put(c.getNif(), c.clone());
    }

    /**
     * Adiciona uma factura a base de dados
     *
     * @param c A factura a adicionar
     */
    public void addFactura (Factura c)
    {
        int nif = c.getNifEmitente();
        Set<Factura> fs = this.getFacturasEmpresa(nif);
        fs.add(c);
        this.facturas.put(nif, fs);
    }

    /**
     * Devolve todas as faturas registadas de um determinado ContribuinteIndividual
     *
     * @param c O Contribuinte Individual
     * @return Todas as faturas registadas de um determinado ContribuinteIndividual
     */
    public Set<Factura> getFacturasIndividual (ContribuinteIndividual c)
    {
        return this.getFacturasIndividual(c.getNif());
    }

    /**
     * Devolve todas as faturas registadas de um determinado NIF de um ContribuinteIndividual
     *
     * @param nif NIF do Contribuinte Individual
     * @return Todas as faturas registadas de um determinado ContribuinteIndividual
     */
    public Set<Factura> getFacturasIndividual (int nif)
    {
        return this.getTodasFacturas()
            .stream()
            .filter(f -> f.getNifCliente() == nif)
            .collect(Collectors.toSet());
    }

    /**
     * Procura por um Contribuinte a partir do seu NIF
     *
     * @param nif NIF do Contribuinte a procurar
     * @return O Contribuinte, ou null caso nao exista nenhum Contribuinte com aquele NIF
     */
    public Contribuinte getContribuinteByNif (int nif)
    {
        Contribuinte ret = this.individuais.get(nif);
        if (ret == null)
            ret = this.empresas.get(nif);
        return ret;
    }

    public Set<Factura> getFacturasIndividualPorClassificar (ContribuinteIndividual c)
    {
        return this.getFacturasIndividual(c)
            .stream()
            .filter(f -> !f.classificada())
            .collect(Collectors.toSet());
    }

    /**
     * Metodo que devolve a deducao total de um determinado ContribuinteIndividual
     */
    public double getDeducao(ContribuinteIndividual c){
        return this.getDeducao(c.getNif());
    }

    /**
     * Metodo que devolve a deducao total de um determinado ContribuinteIndividual
     */
    public double getDeducao(int nif){
        return this.getFacturasIndividual(nif)
            .stream()
            .filter(f -> f.classificada())
            .mapToDouble(f -> {
                Contribuinte c = this.getContribuinteByNif(f.getNifCliente());
                double fc = (c != null) ?
                    c.getCoeficiente():
                    0;

                Contribuinte e = this.getContribuinteByNif(f.getNifEmitente());
                double fe = (e != null) ?
                    e.getCoeficiente():
                    0;

                return f.deducao(fc, fe);
            }).sum();
    }

    /**
     * Metodo que devolve a deducao total de um determinado ContribuinteIndividual
     */
    public double getDespesaTotal(int nif){
        return getFacturasIndividual(nif).stream().filter( f -> f.classificada()).mapToDouble(f -> f.getValor()).sum();
    }

    /**
     * Guarda num ficheiro um objecto da classe BD (esse ficheiro tem sempre o nome DataBase)
     */
    public void gravaTxt() throws IOException
    {
        FileOutputStream fos = new FileOutputStream("DataBase");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.flush();
        oos.close();
    }

    public void removeFactura (Factura f)
    {
        int nif = f.getNifEmitente();
        Set<Factura> fs = this.getFacturasEmpresa(nif);
        fs.remove(f);
        this.facturas.put(nif, fs);
    }

    /**
     * Carrega de num ficheiro um objecto da classe BD (esse ficheiro tem sempre o nome DataBase)
     */
    public static BD carrega() throws IOException, ClassNotFoundException, FileNotFoundException
    {
        FileInputStream fis = new FileInputStream("DataBase");
        ObjectInputStream ois = new ObjectInputStream(fis);
        BD u = (BD) ois.readObject();
        ois.close();
        return u;
    }

    /**
     * Lista todas as facturas de uma dada empresa, emitidas entre duas datas
     *
     * @param e Empresa da qual queremos facturas
     * @param de Data de inicio do intervalo
     * @param ate Data de fim do intervalo
     * @return As facturas da empresa entre as duas datas do intervalo
     */
    public Set<Factura> listaFacturas(ContribuinteEmpresa e, LocalDate de, LocalDate ate)
    {
        return this.getFacturasEmpresa(e)
            .stream()
            .filter(f -> {
                LocalDate d = f.getData();
                return d.isAfter(de) && d.isBefore(ate);
            })
        .collect(Collectors.toSet());
    }

    /**
     * Lista todas as facturas de uma dada empresa
     *
     * @param nif NIF da empresa da qual queremos facturas
     * @return As facturas da empresa
     */
    public Set<Factura> getFacturasEmpresa (int nif)
    {
        Set<Factura> ret = this.facturas.get(nif);

        if (ret == null)
            ret = new HashSet<Factura>();

        return ret.stream()
            .map(Factura::clone)
            .collect(Collectors.toSet());
    }

    /**
     * Lista todas as facturas de uma dada empresa
     *
     * @param e Empresa da qual queremos facturas
     * @return As facturas da empresa, sem uma ordem em particular
     */
    public Set<Factura> getFacturasEmpresa (ContribuinteEmpresa e)
    {
        return this.getFacturasEmpresa(e.getNif());
    }

    /**
     * Lista todas as facturas de uma dada empresa, ordenadas por data
     *
     * @param e Empresa da qual queremos facturas
     * @return As facturas da empresa, ordenadas por data
     */
    public List<Factura> facturaPorData (ContribuinteEmpresa e)
    {
        return this.getFacturasEmpresa(e)
            .stream().sorted(new Comparator<Factura>(){
                public int compare(Factura f1,Factura f2){
                    return f1.getData().compareTo(f2.getData());
                }
            })
        .collect(Collectors.toList());
    }

    /**
     * Lista todas as facturas de uma dada empresa, ordenadas por valor
     *
     * @param e Empresa da qual queremos facturas
     * @return As facturas da empresa, ordenadas por valor
     */
    public List<Factura> facturaPorValor (ContribuinteEmpresa e)
    {
        List<Factura> ret = this.getFacturasEmpresa(e)
            .stream()
            .sorted(new Comparator<Factura>() {
                public int compare(Factura f1,Factura f2){
                    return Double.compare(f1.getValor(),f2.getValor());
                }
            }).collect(Collectors.toList());
        Collections.reverse(ret);
        return ret;
    }

    /**
     * Lista todas as facturas de uma dada empresa, entre duas datas,
     * emitidas a um certo cliente
     *
     * @param e Empresa da qual queremos facturas
     * @param de Data de inicio do intervalo
     * @param ate Data de fim do intervalo
     * @param nif NIF do cliente
     * @return As facturas da empresa
     */
    public Set<Factura> listaFacturas (ContribuinteEmpresa e, LocalDate de, LocalDate ate, int nif)
    {
        return this.listaFacturas(e, de, ate)
            .stream()
            .filter(f -> f.getNifCliente() == nif)
            .collect(Collectors.toSet());
    }

    /**
     * Lista todas as facturas de uma dada empresa, emitidas a um certo cliente,
     * ordenadas por valor
     *
     * @param e Empresa da qual queremos facturas
     * @param nif NIF do cliente
     * @return As facturas da empresa
     */
    public List<Factura> facturaPorValor (ContribuinteEmpresa e, int nif)
    {
        return this.facturaPorValor(e)
            .stream()
            .filter(f -> f.getNifCliente() == nif)
            .collect(Collectors.toList());
    }

    /**
     * Calcula o total facturado por uma empresas entre duas datas
     *
     * @param e A Empresa
     * @param de Data de inicio do intervalo
     * @param ate Data de fim do intervalo
     * @return Total facturado
     */
    public double totalFacturado (ContribuinteEmpresa e, LocalDate de, LocalDate ate)
    {
        return this.listaFacturas(e, de, ate)
            .stream()
            .filter(f -> f.classificada())
            .mapToDouble(f -> f.getValor())
            .sum();
    }
}
