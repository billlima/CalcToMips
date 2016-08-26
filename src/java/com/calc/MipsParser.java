/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.calc;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author mlima
 */
public class MipsParser {

    int minIdCalc = 10000;

    public String parseToMips(List<String> valores) {
        String retorno = getInicio();
        String vetor_valores = "";
        String chamada_calculos = "";
        String comentarios = ""
                + "################################################################<br>"
                + "#     Código Feito através de Notação polonesa inversa<br>"
                + "#     " + valores + "<br>"
                + "################################################################";

        for (int x = 0; x < valores.size(); x++) {
            if (valores.get(x).matches("[+]|[-]|[*]|[/]|(fat)|(sqrt)|(fib)")) {
                while (valores.contains(String.valueOf(minIdCalc))) {
                    minIdCalc++;
                }

                switch (valores.get(x)) {
                    case "+":
                        retorno += getSoma();
                        chamada_calculos += getChamadaCalculo(minIdCalc, "soma");
                        break;
                    case "-":
                        retorno += getSubtracao();
                        chamada_calculos += getChamadaCalculo(minIdCalc, "subtracao");
                        break;
                    case "*":
                        retorno += getMultiplicacao();
                        chamada_calculos += getChamadaCalculo(minIdCalc, "multiplicacao");
                        break;
                    case "/":
                        retorno += getDivisao();
                        chamada_calculos += getChamadaCalculo(minIdCalc, "divisao");
                        break;
                    case "fat":
                        retorno += getFatorial();
                        chamada_calculos += getChamadaCalculo(minIdCalc, "fatorial");
                        break;
                    case "sqrt":
                        retorno += getRaiz();
                        chamada_calculos += getChamadaCalculo(minIdCalc, "raiz");
                        break;
                    case "fib":
                        retorno += getFibonacci();
                        chamada_calculos += getChamadaCalculo(minIdCalc, "fibonacci");
                        break;
                }
                valores = listReplaceAll(valores, valores.get(x), minIdCalc);
                minIdCalc++;
            }
            vetor_valores += valores.get(x) + ",";
        }

        vetor_valores = vetor_valores.substring(0, vetor_valores.length() - 1);

        retorno = retorno.replace("[COMENTARIOS]", comentarios);
        retorno = retorno.replace("[VETOR_VALORES]", vetor_valores);
        retorno = retorno.replace("[TAMANHO_VETOR]", String.valueOf(valores.size()));
        retorno = retorno.replace("[CHAMADA_DOS_CALCULOS]", chamada_calculos);

        return retorno;
    }

    public String getInicio() {
        return "[COMENTARIOS] <br><br>"
                + ".data<br>"
                + "	v_vet:	.word	[VETOR_VALORES] <br>"
                + "	v_size:	.word	[TAMANHO_VETOR] <br>"
                + ".text <br>"
                + " <br>"
                + "main: <br>"
                + "	la	$s5, v_vet		# vetor <br>"
                + "	la	$t0, v_size		# tamanho do array <br>"
                + "	lw 	$s6, ($t0) <br>"
                + "	li 	$s7, 0			# index loop <br>"
                + "	 <br>"
                + "loop1: <br>"
                + "	beq	$s6, $s7, end <br>"
                + "	lw 	$a0, 0($s5) <br>"
                + "	 <br>"
                + "     [CHAMADA_DOS_CALCULOS]"
                + "	 <br>"
                + "	jal 	push	 <br>"
                + "endLoop: 	 <br>"
                + "	addi	$s5, $s5, 4 <br>"
                + "	add	$s7, $s7, 1	 <br>"
                + "	j 	loop1 <br>"
                + "end: <br>"
                + "	jal 	pop <br>"
                + "	move	$a0, $v0 <br>"
                + "	li 	$v0, 1 <br>"
                + "	syscall	 <br>"
                + "	li	$v0, 10 <br>"
                + "	syscall <br>"
                + "	 <br>"
                + "push: 	# parametro $a0	 <br>"
                + "	addi 	$sp, $sp, -4 		# incrementa 1 no indice da pilha <br>"
                + "	sw	$a0, 0($sp)		# salva $t1 na pilha <br>"
                + "	jr	$ra	 <br>"
                + "pop:	# retorno $v0 <br>"
                + "	lw 	$v0, 0($sp)		# pega o valor de cima da pilha <br>"
                + "	sw	$zero, ($sp)		# seta como zero somente para visualizacao <br>"
                + "	addi	$sp, $sp, 4		# decrementa 1 no indice da pilha <br>"
                + "	jr	$ra <br>"
                + "	 <br>"
                + "endErro: <br>"
                + "	li	$v0, 10 <br>"
                + "	syscall <br>"
                + "	 <br>"
                + "#-------------------------------------------CALCULOS <br> <br> <br>";
    }

    public String getSoma() {
        return "# Soma os dois ultimos itens da pilha (pop,pop,push)  <br>"
                + "soma:	 <br>"
                + "	jal	pop  <br>"
                + "	move	$s0, $v0  <br>"
                + "	jal	pop  <br>"
                + "	move	$s1, $v0  <br>"
                + "	add	$a0, $s0, $s1  <br>"
                + "	jal	push  <br>"
                + "	j	endLoop  <br> <br>";
    }

    public String getSubtracao() {
        return "# Subrai o penúltimo item da pilha pelo ultimo item da pilha (pop,pop,push)  <br>"
                + "subtracao:  <br>"
                + "	jal	pop  <br>"
                + "	move	$s1, $v0  <br>"
                + "	jal	pop  <br>"
                + "	move	$s0, $v0  <br>"
                + "	sub	$a0, $s0, $s1  <br>"
                + "	jal 	push  <br>"
                + "	j	endLoop  <br> <br>";
    }

    public String getMultiplicacao() {
        return "# Multiplica os dois últimos ítens da pilha (pop,pop,push)  <br>"
                + "multiplicacao: 	 <br>"
                + "	jal	pop <br>"
                + "	move	$s0, $v0 <br>"
                + "	jal	pop <br>"
                + "	move	$s1, $v0 <br>"
                + "	li	$t1, 0			# contador  <br>"
                + "	li	$t2, 0			# resultado  <br>"
                + "	li	$t3, 0 			# para saber se $s0 < 0  <br>"
                + "	li	$t4, 0			# para saber se $s1 < 0 <br>"
                + "	beq	$s0, $zero, endMultiplicacao <br>"
                + "	beq	$s1, $zero, endMultiplicacao <br>"
                + "	blt	$s0, $zero, menorQueZero1Mult  <br>"
                + "continuaMultiplicacao:   <br>"
                + "	blt	$s1, $zero, menorQueZero2Mult    <br>"
                + "loopMultiplicacao:  <br>"
                + "	add	$t2, $t2, $s0    <br>"
                + "	add	$t1, $t1, 1  <br>"
                + "	blt	$t1, $s1 loopMultiplicacao		  <br>"
                + "	j	confereSinalMult <br>"
                + "menorQueZero1Mult:   <br>"
                + "	li	$t3, 1	  <br>"
                + "	sub	$s0, $zero, $s0  <br>"
                + "	j	continuaMultiplicacao  <br>"
                + "menorQueZero2Mult:   <br>"
                + "	li	$t4, 1	  <br>"
                + "	sub	$s1, $zero, $s1  <br>"
                + "	j	loopMultiplicacao  <br>"
                + "confereSinalMult: <br>"
                + "	beq	$t3, $t4, endMultiplicacao  <br>"
                + "	sub	$t2, $zero, $t2  <br>"
                + "endMultiplicacao:  <br>"
                + "	move	$a0, $t2  <br>"
                + "	jal	push  <br>"
                + "	j	endLoop ";
    }

    public String getDivisao() {
        return "# Divide o penúltimo item da pilha pelo último (pop,pop,push)  <br>"
                + "divisao:  <br>"
                + "	jal	pop  <br>"
                + "	move	$s1, $v0  <br>"
                + "	jal	pop  <br>"
                + "	move	$s0, $v0  <br>"
                + "	li	$s2, 0			# resultado 	 <br>"
                + "	li	$s3, 0			# para saber se dividendo é menor que zero  <br>"
                + "	li	$s4, 0			# para saber se divisor é menor que zero  <br>"
                + "	beq	$s1, $zero, end <br>"
                + "	blt	$s0, $zero, menorQueZero1 <br>"
                + "continua:  <br>"
                + "	blt	$s1, $zero, menorQueZero2  <br>"
                + "loopDivisao:  <br>"
                + "	sub	$s0, $s0, $s1  <br>"
                + "	blt	$s0, $zero, confereSinal  <br>"
                + "	add	$s2, $s2, 1    <br>"
                + "	j 	loopDivisao  <br>"
                + "menorQueZero1:  <br>"
                + "	li	$s3, 1	 <br>"
                + "	sub	$s0, $zero, $s0 <br>"
                + "	j	continua <br>"
                + "menorQueZero2:  <br>"
                + "	li	$s4, 1	 <br>"
                + "	sub	$s1, $zero, $s1 <br>"
                + "	j	loopDivisao <br>"
                + "confereSinal: <br>"
                + "	beq	$s3, $s4, endDivisao <br>"
                + "	sub	$s2, $zero, $s2 <br>"
                + "endDivisao:  <br>"
                + "	move	$a0, $s2  <br>"
                + "	jal	push  <br>"
                + "	j	endLoop <br><br>";
    }

    public String getRaiz() {
        return "# Faz a raiz quadrada do último ítem da pilha (pop,push) <br>"
                + "raiz: <br>"
                + "	jal	pop <br>"
                + "	move	$s0, $v0 <br>"
                + "	addi 	$s0, $s0, 1 		# adiciona 1 e adiciona em $s6 <br>"
                + "	srl  	$s0, $s0, 1		# divide $s0 por dois (deslocamento a direita) <br>"
                + "	li	$s1, 0			# carrega $s1 com 0 <br>"
                + "loopRaiz:	 <br>"
                + "	sub 	$s0, $s0, $s1  		# subtrai $s1 de $s0  <br>"
                + "	addi 	$s1, $s1, 1		# adiciona 1 a $s5 <br>"
                + "	slt 	$s2, $s0, $s1		# se $s0 < $s1 adiciona 1 a $s2 <br>"
                + "	bne 	$s2, $zero, endRaiz	# se $s2 != 0 vai para o end <br>"
                + "	bne 	$s0, $s2, loopRaiz  	# se $s0 != $s2 retorna para o loop	 <br>"
                + "endRaiz:  <br>"
                + "	move	$a0, $s1 <br>"
                + "	jal 	push <br>"
                + "	j	endLoop <br> <br>";
    }

    public String getFatorial() {
        return "# Faz o fatorial do último ítem da pilha (pop,push) <br>"
                + "fatorial:                   <br>"
                + "      	jal 	pop	 <br>"
                + "      	move	$a0, $v0 <br>"
                + "      	move	$s1, $sp		# move o stackPointer para $s1 pois o fatorial usa o stack <br>"
                + "      	jal 	fat      		# call fact(n) <br>"
                + "      	move	$a0, $v0		 <br>"
                + "      	move	$sp, $s1		# retorna o stackPointer para o $sp <br>"
                + "	jal 	push <br>"
                + "	j	endLoop <br>"
                + " <br>"
                + "fat: <br>"
                + "      	sub 	$sp, $sp, 8   		# Ajusta a stack para 2 items <br>"
                + "      	sw 	$ra, 4($sp)   		# Guarda endereço de retorno <br>"
                + "      	sw 	$a0, 0($sp)   		# Guarda argumento n <br>"
                + " <br>"
                + "      	addi 	$t1, $zero, 1 <br>"
                + "      	slt 	$t0, $a0, $t1   	# testa se n < 1 <br>"
                + "      	beq 	$t0, $zero, L1  	# Se n >= 1, vai fazer outra chamada <br>"
                + " <br>"
                + "      	li 	$v0, 1      		# Se não for devolve 1 <br>"
                + "      	add 	$sp, $sp, 8   		# liberta o espaço da stack antes de <br>"
                + "      	jr 	$ra			# retornar <br>"
                + " <br>"
                + "L1:    <br>"
                + "	sub $a0, $a0, 1   		# Nova chamada: novo argumento (n - 1) <br>"
                + "      	jal fat      			# chama fat com (n - 1) <br>"
                + " <br>"
                + "        # Ponto de retorno da chamada recursiva: <br>"
                + "      	lw 	$a0, 0($sp)   		# Recupera o argumento passado <br>"
                + "      	lw 	$ra, 4($sp)   		# Recupera o endereço de retorno <br>"
                + "      	add  	$sp, $sp, 8   		# Liberta o espaço da stack <br>"
                + " <br>"
                + "      	mul 	$v0,$a0,$v0   		# Calcula n * fact (n - 1) <br>"
                + "      	jr 	$ra            		# Retorna com o resultado <br> <br>";
    }

    public String getFibonacci() {
        return "# Faz o cálculo fibonacci do último ítem da pilha (pop,push)    <br>"
                + "fibonacci:   <br>"
                + "      	jal 	pop	 <br>"
                + "      	move	$s0, $v0 <br>"
                + "	li	$s1, 0			# atual <br>"
                + "	li	$s2, 0			# anterior <br>"
                + "	li 	$s3, 0			# contador <br>"
                + " <br>"
                + "fibLoop:	 <br>"
                + "	add	$s3, $s3, 1		# incrementa contador <br>"
                + "	blt	$s0, $s3, fibFim	# se valor < contador <br>"
                + "	beq	$s3, 1, fibIgual	# se atual = 1	 <br>"
                + "	j	fibDiferente <br>"
                + "	 <br>"
                + "fibIgual: <br>"
                + "	li	$s1, 1			# atual = 1 <br>"
                + "	li	$s2, 0			# anterior = 0 <br>"
                + "	j 	fibLoop <br>"
                + "	 <br>"
                + "fibDiferente: <br>"
                + "	add	$s1, $s1, $s2		# atual += anterior <br>"
                + "	sub	$s2, $s1, $s2		# anterior = atual - anterior <br>"
                + "	j	fibLoop <br>"
                + "	 <br>"
                + "fibFim: <br>"
                + "	move	$a0, $s1 <br>"
                + "	jal	push <br>"
                + "	j	endLoop <br> <br>";
    }

    public boolean arrayContains(Object[] array, Integer find) {
        for (Object obj : array) {
            if (obj.toString().replaceAll(" ", "").equals(find.toString())) {
                return true;
            }
        }
        return false;
    }

    public List<String> listReplaceAll(List<String> list, String find, Integer replace) {
        for (int x = 0; x < list.size(); x++) {
            if (list.get(x).equals(find)) {
                list.set(x, replace.toString());
            }
        }
        return list;
    }

    public String getChamadaCalculo(int id, String calculo) {
        return "	beq	$a0, " + id + ", " + calculo + " <br>";
    }

}
