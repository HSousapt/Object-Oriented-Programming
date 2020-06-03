import java.util.HashSet;
import java.util.TreeSet;
import java.util.Set;
import java.time.LocalDate;

public class DBBuilder
{
    public static void main (String[] args)
    {
        BD b = new BD();

        { /* Adiciona uma empresa interior */
            Set<Atividades> ativs = new HashSet();
            ativs.add(new Transportes());

            int nif = 1;
            String mail = "1";
            String name = "1";
            String morada = "1";
            String pass = "1";

            ContribuinteEmpresaInterior c = new ContribuinteEmpresaInterior(ativs, nif, mail, name, morada, pass);
            b.addContribuinteEmpresa(c);
        }

        { /* Adiciona uma empresa normal */
            Set<Atividades> ativs = new HashSet();
            ativs.add(new Transportes());

            int nif = 2;
            String mail = "2";
            String name = "2";
            String morada = "2";
            String pass = "2";

            ContribuinteEmpresa c = new ContribuinteEmpresa(ativs, nif, mail, name, morada, pass);
            b.addContribuinteEmpresa(c);
        }

        { /* Adiciona um contribuinte normal */
            Set<Integer> nfiscais = new TreeSet();
            nfiscais.add(4);
            nfiscais.add(5);

            int nif = 3;
            String mail = "3";
            String name = "3";
            String morada = "3";
            String pass = "3";

            ContribuinteIndividual c = new ContribuinteIndividual(nif, mail, name, morada, pass, nfiscais);
            b.addContribuinteIndividual(c);
        }

        { /* Adiciona um contribuinte normal */
            Set<Integer> nfiscais = new TreeSet();

            int nif = 4;
            String mail = "4";
            String name = "4";
            String morada = "4";
            String pass = "4";

            ContribuinteIndividual c = new ContribuinteIndividual(nif, mail, name, morada, pass, nfiscais);
            b.addContribuinteIndividual(c);
        }

        { /* Adiciona um contribuinte normal */
            Set<Integer> nfiscais = new TreeSet();

            int nif = 5;
            String mail = "5";
            String name = "5";
            String morada = "5";
            String pass = "5";

            ContribuinteIndividual c = new ContribuinteIndividual(nif, mail, name, morada, pass, nfiscais);
            b.addContribuinteIndividual(c);
        }

        { /* Adiciona uma factura */
            int nif_emitente = 1;
            String descricao = "tomate";
            LocalDate data=LocalDate.now();
            int nif_cliente=3;
            String emitente=new String();
            double valor=10;
            Atividades a = new Transportes();

            Factura c = new Factura(nif_emitente, emitente, data, nif_cliente, descricao, valor, a);
            b.addFactura(c);
        }

        { /* Adiciona uma factura */
            int nif_emitente = 1;
            String descricao = "batatas";
            LocalDate data=LocalDate.now();
            int nif_cliente = 4;
            String emitente=new String();
            double valor= 10;
            Atividades a = new Transportes();

            Factura c = new Factura(nif_emitente, emitente, data, nif_cliente, descricao, valor, a);
            b.addFactura(c);
        }

        { /* Adiciona uma factura */
            int nif_emitente = 1;
            String descricao = "cebolas";
            LocalDate data = LocalDate.now();
            int nif_cliente = 5;
            String emitente = new String();
            double valor = 10;
            Atividades a = new Saude();

            Factura c = new Factura(nif_emitente, emitente, data, nif_cliente, descricao, valor, a);
            b.addFactura(c);
        }

        { /* Adiciona uma factura */
            int nif_emitente = 1;
            String descricao = "cebolas descascadas";
            LocalDate data = LocalDate.now();
            int nif_cliente = 3;
            String emitente = new String();
            double valor = 10;

            Factura c = new Factura(nif_emitente, emitente, data, nif_cliente, descricao, valor);
            b.addFactura(c);
        }

        try {
            b.gravaTxt();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
