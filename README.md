# Sistema de Gestão de Clínica Médica
**Instituição:** Universidade Kimpa Vita  
**Curso:** Engenharia Informática — 2º Ano  
**Disciplina:** Linguagem de Programação IV  
**Docente:** Moyo Kanivengidio  
**Grupo:** AXSTORE  
**Data de início:** 30/05/2026  

---

## Descrição do Projeto

Sistema desktop desenvolvido em Java para gerir as operações diárias de uma clínica médica. O sistema permite o cadastro de pacientes, médicos, agendamento de consultas, prontuários eletrónicos e controlo de acesso por perfil de utilizador.

---

## Tecnologias Utilizadas

- **Linguagem:** Java 8+
- **Interface Gráfica:** Java Swing / JavaFX
- **Base de Dados:** MySQL
- **Conexão BD:** JDBC
- **Arquitetura:** MVC + DAO
- **IDE:** IntelliJ IDEA

---

## Base de Dados

**Nome:** `clinica_medica`

### Tabelas principais

| Tabela | Descrição |
|--------|-----------|
| `usuarios` | Utilizadores do sistema com perfis de acesso |
| `medicos` | Médicos cadastrados na clínica |
| `pacientes` | Pacientes cadastrados |
| `consultas` | Agendamentos de consultas |
| `prontuarios` | Prontuários eletrónicos |

### Ligações entre tabelas

- A tabela `medicos` tem uma coluna `usuario_id` que liga cada médico ao seu utilizador de login
- A tabela `pacientes` terá uma coluna `usuario_id` para login próprio dos pacientes

---

## Perfis de Utilizador

| Perfil | Login | Senha | Permissões |
|--------|-------|-------|------------|
| ADMINISTRADOR | admin | 1234 | Acesso total ao sistema |
| RECEPCIONISTA | user | 1234 | Apenas cadastro e gestão de pacientes |
| MEDICO (Pedro) | pedro | 1234 | Apenas as suas consultas e pacientes |
| MEDICO (Ramos) | ramos | 1234 | Apenas as suas consultas e pacientes |
| MEDICO (Oscar Manuel) | oscar | 1234 | Apenas as suas consultas e pacientes |
| PACIENTE | (login do paciente) | 1234 | Apenas as suas próprias consultas |

---

## Regras de Acesso por Perfil

### Administrador
- Acesso total: pacientes, médicos, consultas, prontuários e relatórios

### Recepcionista
- Pode cadastrar, editar e consultar pacientes
- Sem acesso a médicos, consultas, prontuários e relatórios

### Médico
- Ao fazer login, o sistema identifica o médico pelo `usuario_id`
- Vê apenas as consultas marcadas com ele próprio
- Sem acesso a dados de outros médicos

### Paciente
- Ao fazer login, o sistema identifica o paciente pelo `usuario_id`
- Vê apenas as suas próprias consultas
- Sem acesso a outras funcionalidades

---

## Arquitetura do Sistema (MVC + DAO)

```
src/
├── model/
│   ├── Usuario.java
│   ├── Medico.java
│   ├── Paciente.java
│   └── Consulta.java
├── view/
│   ├── TelaLogin.java
│   ├── TelaMenu.java
│   ├── TelaPacientes.java
│   ├── TelaMedicos.java
│   └── TelaConsultas.java
├── controller/
│   ├── LoginController.java
│   ├── PacienteController.java
│   └── ConsultaController.java
└── dao/
    ├── ConexaoDB.java
    ├── UsuarioDAO.java
    ├── MedicoDAO.java
    ├── PacienteDAO.java
    └── ConsultaDAO.java
```

---

## Fases de Implementação

### Fase 1 — Recepcionista ✅
Restrição no `TelaMenu.java` para mostrar apenas o módulo de Pacientes quando o perfil é RECEPCIONISTA.

### Fase 2 — Médico ✅
Uso do `usuario_id` na tabela `medicos` para filtrar as consultas por médico logado.

### Fase 3 — Paciente (em curso)
- Adicionar coluna `usuario_id` na tabela `pacientes`
- Criar perfil PACIENTE na tabela `usuarios`
- Criar tela exclusiva para o paciente ver as suas consultas

---

## Como Correr o Projeto

1. Instalar o MySQL e criar a base de dados `clinica_medica`
2. Correr o script SQL (`clinica_medica.sql`) para criar as tabelas e inserir dados de exemplo
3. Abrir o projeto no IntelliJ IDEA
4. Configurar a ligação à base de dados no ficheiro `ConexaoDB.java`
5. Correr o ficheiro `Main.java`

---

## Critérios de Avaliação

| Critério | Peso |
|----------|------|
| Funcionalidade e Robustez | 40% |
| POO e Arquitetura (MVC + DAO) | 25% |
| Interface Gráfica e Usabilidade | 15% |
| Banco de Dados e Persistência | 10% |
| Documentação e Apresentação | 10% |

---

## Entregáveis

- Código-fonte completo no GitHub
- Script SQL completo (DDL + DML)
- Relatório Técnico em PDF
- Apresentação oral com demonstração ao vivo
