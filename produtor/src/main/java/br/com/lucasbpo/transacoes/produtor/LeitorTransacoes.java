package br.com.lucasbpo.transacoes.produtor;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;

public class LeitorTransacoes {

    public static List<Transacao> lerTodos() {

        try {
            final InputStream fileInput = new ClassPathResource("transacoes.csv").getInputStream();
            final InputStreamReader reader = new InputStreamReader(fileInput);

            return CSVFormat.DEFAULT.builder()
                    .setHeader()
                    .get()
                    .parse(reader)
                    .stream()
                    .map(LeitorTransacoes::converter)
                    .toList();

        } catch (IOException e) {
            throw new RuntimeException("Erro ao realizar a leitura do arquivo CSV: " + e.getMessage(), e);
        }
    }

    private static Transacao converter(CSVRecord record) {
        final Integer codigo = Integer.valueOf(record.get("codigo"));
        final Double valor = Double.valueOf(record.get("valor"));

        return new Transacao(
                codigo,
                record.get("cedente"),
                record.get("pagador"),
                valor,
                record.get("vencimento")
        );
    }
}
