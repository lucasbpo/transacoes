package br.com.lucasbpo.transacoes.receitafederal;

import java.io.Serializable;

public record Transacao(
        Integer codigo,
        String cedente,
        String pagador,
        Double valor,
        String vencimento
) implements Serializable {

    @Override
    public String toString() {
        return "Transacao{" +
                "codigo=" + codigo +
                ", cedente='" + cedente + '\'' +
                ", pagador='" + pagador + '\'' +
                ", valor=" + valor +
                ", vencimento='" + vencimento + '\'' +
                '}';
    }
}
