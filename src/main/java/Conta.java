import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Conta {
	
    private double saldo; // Saldo da conta
    private RuntimeException exception; // Variável para lidar com exceções

    // Configuração: Cliente especial com saldo inicial negativo
    @Given("^Um cliente especial com saldo atual de -(\\d+) reais$")
    public void um_cliente_especial_com_saldo_atual_de_reais(int sI) {
    	saldo = -sI;
    }
    
    // Configuração: Cliente comum com um saldo inicial negativo
    @Given("^Um cliente comum com saldo atual de -(\\d+) reais$")
    public void um_cliente_comum_com_saldo_atual_de_reais(int sI) {
    	saldo = -sI;
    }

    // Ação: Solicitar um saque
    @When("^for solicitado um saque no valor de (\\d+) reais$")
    public void for_solicitado_um_saque_no_valor_de_reais(int vS) {
        if (vS <= 0) {
            exception = new RuntimeException("Valor de saque inválido."); // Capturar exceções
        } else if (saldo < vS) {
            exception = new RuntimeException("Saldo insuficiente para o saque."); // Capturar exceções
        } else {
            saldo -= vS; // Realizar o saque
        }
    }

    // Verificação: Saque efetuado com sucesso
    @Then("^deve efetuar o saque e atualizar o saldo da conta para -(\\d+) reais$")
    public void deve_efetuar_o_saque_e_atualizar_o_saldo_da_conta_para_reais(int novoSaldo) {
        if (saldo == -novoSaldo) {
            System.out.println("Saque realizado! Seu novo saldo é: " + saldo);
        } else {
            System.out.println("Ops! Algo deu errado ao tentar realizar o saque.");
        }
    }

    // Verificação: Saque não deve ser efetuado devido a saldo insuficiente
    @Then("Não deve efetuar o saque e deve retornar a mensagem Saldo Insuficiente.")
    public void nao_deve_efetuar_o_saque_e_deve_retornar_a_mensagem_Saldo_Insuficiente() {
        // Verificar se uma exceção foi lançada
        if (exception != null) {
            // Verificar se a mensagem da exceção é "Saldo insuficiente para o saque."
            if ("Saldo insuficiente para o saque.".equals(exception.getMessage())) {
                System.out.println("Não foi possível realizar o saque. Seu saldo é insuficiente.");
            } else {
                System.out.println("Ocorreu um erro desconhecido.");
            }
        } else {
            System.out.println("Saque realizado com sucesso!");
        }
    }
}