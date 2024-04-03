public class ArvoreAVL {
    private No raiz;

    public ArvoreAVL() {
        raiz = null;
    }

    public void inserir(int valor) {
        No novoNo = new No(valor);
        if (raiz == null) {
            raiz = novoNo;
        } else {
            inserir(raiz, novoNo);
        }
    }

    private void inserir(No raiz, No novoNo) {
        if (novoNo.getValor() < raiz.getValor()) {
            if (raiz.getEsquerda() == null) {
                raiz.setEsquerda(novoNo);
                novoNo.setPai(raiz);
                verificarBalanceamento(raiz);
            } else {
                inserir(raiz.getEsquerda(), novoNo);
            }
        } else if (novoNo.getValor() > raiz.getValor()) {
            if (raiz.getDireita() == null) {
                raiz.setDireita(novoNo);
                novoNo.setPai(raiz);
                verificarBalanceamento(raiz);
            } else {
                inserir(raiz.getDireita(), novoNo);
            }
        }
    }

    private void verificarBalanceamento(No no) {
        setBalanceamento(no);
        int balanceamento = no.getBalanceamento();
        if (balanceamento == -2) {
            if (altura(no.getEsquerda().getEsquerda()) >= altura(no.getEsquerda().getDireita())) {
                no = rotacaoDireita(no);
            } else {
                no = duplaRotacaoEsquerdaDireita(no);
            }
        } else if (balanceamento == 2) {
            if (altura(no.getDireita().getDireita()) >= altura(no.getDireita().getEsquerda())) {
                no = rotacaoEsquerda(no);
            } else {
                no = duplaRotacaoDireitaEsquerda(no);
            }
        }
        if (no.getPai() != null) {
            verificarBalanceamento(no.getPai());
        } else {
            raiz = no;
        }
    }

    private No rotacaoDireita(No inicial) {        
        No esquerda = inicial.getEsquerda();
        esquerda.setPai(inicial.getPai());
        inicial.setEsquerda(esquerda.getDireita());
        if (inicial.getEsquerda() != null) {
            inicial.getEsquerda().setPai(inicial);
        }
        esquerda.setDireita(inicial);
        inicial.setPai(esquerda);
        if (esquerda.getPai() != null) {
            if (esquerda.getPai().getDireita() == inicial) {
                esquerda.getPai().setDireita(esquerda);
            } else if (esquerda.getPai().getEsquerda() == inicial) {
                esquerda.getPai().setEsquerda(esquerda);
            }
        }
        setBalanceamento(inicial);
        setBalanceamento(esquerda);
        return esquerda;
    }

    private No rotacaoEsquerda(No inicial) {
        No direita = inicial.getDireita();
        direita.setPai(inicial.getPai());
        inicial.setDireita(direita.getEsquerda());
        if (inicial.getDireita() != null) {
            inicial.getDireita().setPai(inicial);
        }
        direita.setEsquerda(inicial);
        inicial.setPai(direita);
        if (direita.getPai() != null) {
            if (direita.getPai().getDireita() == inicial) {
                direita.getPai().setDireita(direita);
            } else if (direita.getPai().getEsquerda() == inicial) {
                direita.getPai().setEsquerda(direita);
            }
        }
        setBalanceamento(inicial);
        setBalanceamento(direita);
        return direita;
    }

    private No duplaRotacaoEsquerdaDireita(No inicial) {
        inicial.setEsquerda(rotacaoEsquerda(inicial.getEsquerda()));
        return rotacaoDireita(inicial);
    }

    private No duplaRotacaoDireitaEsquerda(No inicial) {
        inicial.setDireita(rotacaoDireita(inicial.getDireita()));
        return rotacaoEsquerda(inicial);
    }

    public void remover(int valor) {
        remover(raiz, valor);
    }

    private void remover(No raiz, int valor) {
        if (raiz == null) {
            return;
        } else {
            if (raiz.getValor() > valor) {
                remover(raiz.getEsquerda(), valor);
            } else if (raiz.getValor() < valor) {
                remover(raiz.getDireita(), valor);
            } else {
                removerNo(raiz);
            }
        }
    }

    private void removerNo(No no) {
        No pai = no.getPai();
        if (no.getEsquerda() == null && no.getDireita() == null) {
            if (pai == null) {
                raiz = null;
            } else {
                if (pai.getEsquerda() == no) {
                    pai.setEsquerda(null);
                } else {
                    pai.setDireita(null);
                }
                verificarBalanceamento(pai);
            }
            no = null;
            return;
        }
        if (no.getEsquerda() != null) {
            No substituto = no.getEsquerda();
            while (substituto.getDireita() != null) {
                substituto = substituto.getDireita();
            }
            no.setValor(substituto.getValor());
            removerNo(substituto);
        } else {
            No substituto = no.getDireita();
            while (substituto.getEsquerda() != null) {
                substituto = substituto.getEsquerda();
            }
            no.setValor(substituto.getValor());
            removerNo(substituto);
        }
    }

    private int altura(No no) {
        if (no == null) {
            return -1;
        }
        if (no.getEsquerda() == null && no.getDireita() == null) {
            return 0;
        } else if (no.getEsquerda() == null) {
            return 1 + altura(no.getDireita());
        } else if (no.getDireita() == null) {
            return 1 + altura(no.getEsquerda());
        } else {
            return 1 + Math.max(altura(no.getEsquerda()), altura(no.getDireita()));
        }
    }

    private void setBalanceamento(No no) {
        no.setBalanceamento(altura(no.getDireita()) - altura(no.getEsquerda()));
    }

    public String imprimir() {
        return this.nodeToString(this.raiz, 0, "Raiz: ");
    }

    private String nodeToString(No no, int profundidade, String prefixo) {
        if (no == null) {
            return prefixo + "null\n";
        }

        String resultado = "";
        resultado += prefixo + "Valor: " + no.getValor() + " (FB = " + no.getBalanceamento() + ")\n";
        
        if (no.getEsquerda() != null || no.getDireita() != null) {
            String novoPrefixo = " ".repeat(profundidade * 4) + "↳ 𝐄: ";
            resultado += this.nodeToString(no.getEsquerda(), profundidade + 1, novoPrefixo);
        }

        if (no.getDireita() != null) {
            String novoPrefixo = " ".repeat(profundidade * 4) + "↳ 𝐃: ";
            resultado += this.nodeToString(no.getDireita(), profundidade + 1, novoPrefixo);
        }

        return  resultado;
    }
}
