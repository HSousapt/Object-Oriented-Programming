SRC := \
    Atividades.java                  \
    BD.java                          \
    Contribuinte.java                \
    ContribuinteEmpresa.java         \
    ContribuinteEmpresaInterior.java \
    ContribuinteFamiliaNumerosa.java \
    ContribuinteIndividual.java      \
    DBBuilder.java                   \
    Educacao.java                    \
    Entretenimento.java              \
    Factura.java                     \
    JavaFactura.java                 \
    ReducaoImposto.java              \
    Restauracao.java                 \
    Saude.java                       \
    SemAtividade.java                \
    Transportes.java                 \

OBJS := $(SRC:.java=.class)

all: $(OBJS)

%.class: %.java
	@javac $<

run: DataBase
	@java JavaFactura

DataBase: all DBBuilder.class
	@java DBBuilder

docs: $(SRC)
	@javadoc -d doc $(SRC)

clean:
	$(RM) -r $(OBJS) doc
