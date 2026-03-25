[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-2e0aaae1b6195c2367325f4f02e2d04e9abb55f0b24a779b69b11b9e10269abc.svg)](https://classroom.github.com/online_ide?assignment_repo_id=23286637&assignment_repo_type=AssignmentRepo)
# AED II - Oficina 3: Contagem de operações e desempenho de algoritmos

Este repositório contém uma solução em Java para as tarefas de análise de desempenho (Tarefa 0, 1 e 2) descritas no enunciado.

## Aluno

* Nome completo do aluno: Lucas Carvalho Peres

## Estrutura do projeto

- `src/App.java`: implementação dos 4 algoritmos, instrumentação com contagem de operações e medições de tempo.
- `resultado.csv`: gerado em runtime com dados de `algoritmo,teste,n,operacoes,ms`.
- `graficos_operacoes.png`, `graficos_tempo.png`: gerados em runtime com gráficos de operação e tempo.
- `doc/`: documentação e instruções fornecidas pelo template.

## Algoritmos implementados

1. `codigo1`: percorre vetor de `0` a `n` em passo 2 (complexidade O(n)).
2. `codigo2`: loop com `k` decrescendo por fatores de 2 e iteração interna até `k` (complexidade ~O(n)).
3. `codigo3`: Selection sort (complexidade O(n²)).
4. `codigo4`: Fibonacci recursivo (complexidade O(2^n)).

## Como executar

No terminal (Windows):

```powershell
cd "c:\Users\carva\OneDrive\Área de Trabalho\oficina-3-contagem-de-opera-es-lucas-8844"
javac src/App.java
java -cp src App
```

Saída esperada:
- `resultado.csv` com dados de operação e tempo.
- `graficos_operacoes.png` e `graficos_tempo.png` com gráficos de desempenho.

## Tarefas atendidas

- Tarefa 1: execuções de `Teste Grande` para algoritmo 1 e 2, `Teste Medio` para algoritmo 3, `Teste Pequeno` para algoritmo 4.
- Tarefa 2: gráfico de operações e tempo produzido em `graficos.html` + dados em `resultado.csv`.

## Observações

Se o repositório estiver em outro folder, ajuste caminho e execute novamente. O resultado consiste em Paste/Copiar para planilha ou abrir o HTML.


