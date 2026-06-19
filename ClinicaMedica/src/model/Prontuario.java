package model;

public class Prontuario {

    private int id;
    private int consultaId;
    private String diagnostico;
    private String prescricao;
    private String observacoes;
    private String dataRegistro;

    // Construtor vazio
    public Prontuario() {}

    // Construtor completo
    public Prontuario(int id, int consultaId, String diagnostico,
                      String prescricao, String observacoes, String dataRegistro) {
        this.id = id;
        this.consultaId = consultaId;
        this.diagnostico = diagnostico;
        this.prescricao = prescricao;
        this.observacoes = observacoes;
        this.dataRegistro = dataRegistro;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getConsultaId() { return consultaId; }
    public void setConsultaId(int consultaId) { this.consultaId = consultaId; }

    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }

    public String getPrescricao() { return prescricao; }
    public void setPrescricao(String prescricao) { this.prescricao = prescricao; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public String getDataRegistro() { return dataRegistro; }
    public void setDataRegistro(String dataRegistro) { this.dataRegistro = dataRegistro; }

    @Override
    public String toString() {
        return "Prontuário #" + id + " - Consulta #" + consultaId;
    }
}