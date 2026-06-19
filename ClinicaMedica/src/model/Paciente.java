package model;
public class Paciente {
    private int id;
    private String nome;
    private String bi;
    private String dataNascimento;
    private String telefone;
    private String email;
    private String endereco;
    private String historicoMedico;
    private boolean ativo;
    // Construtor vazio
    public Paciente() {}
    // Construtor completo
    public Paciente(int id, String nome, String bi, String dataNascimento,
                    String telefone, String email, String endereco,
                    String historicoMedico, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.bi = bi;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
        this.historicoMedico = historicoMedico;
        this.ativo = ativo;
    }
    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getBi() { return bi; }
    public void setBi(String bi) { this.bi = bi; }
    public String getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(String dataNascimento) { this.dataNascimento = dataNascimento; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public String getHistoricoMedico() { return historicoMedico; }
    public void setHistoricoMedico(String historicoMedico) { this.historicoMedico = historicoMedico; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
    @Override
    public String toString() {
        return nome + " (BI: " + bi + ")";
    }
}