import java.util.Random;

/** 
 * MIT License
 *
 * Copyright(c) 2024-255 João Caram <caram@pucminas.br>
 *                       Eveline Alonso Veloso
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

public class App {
    static final int[] tamanhosTesteGrande =  { 31_250_000, 62_500_000, 125_000_000, 250_000_000, 500_000_000 };
    static final int[] tamanhosTesteMedio =   {     12_500,     25_000,      50_000,     100_000,     200_000 };
    static final int[] tamanhosTestePequeno = {          3,          6,          12,          24,          48 };
    static Random aleatorio = new Random(42);
    static long operacoes;
    static double nanoToMilli = 1.0/1_000_000;

    /**
     * Código de teste 1. Este método...
     * @param vetor Vetor com dados para teste.
     * @return Uma resposta que significa....
     */
    static int codigo1(int[] vetor) {
        int resposta = 0;
        for (int i = 0; i < vetor.length; i += 2) {
            resposta += vetor[i]%2;
        }
        return resposta;
    }

    /**
     * Código de teste 2. Este método...
     * @param vetor Vetor com dados para teste.
     * @return Uma resposta que significa....
     */
    static int codigo2(int[] vetor) {
        int contador = 0;
        for (int k = (vetor.length - 1); k > 0; k /= 2) {
            for (int i = 0; i <= k; i++) {
                contador++;
            }

        }
        return contador;
    }

    /**
     * Código de teste 3. Este método...
     * @param vetor Vetor com dados para teste.
     */
    static void codigo3(int[] vetor) {
        for (int i = 0; i < vetor.length - 1; i++) {
            int menor = i;
            for (int j = i + 1; j < vetor.length; j++) {
                if (vetor[j] < vetor[menor])
                    menor = j;
            }
            int temp = vetor[i];
            vetor[i] = vetor[menor];
            vetor[menor] = temp;
        }
    }

    /**
     * Código de teste 4 (recursivo). Este método...
     * @param n Ponto inicial do algoritmo
     * @return Um inteiro que significa...
     */
    static int codigo4(int n) {
        if (n <= 2)
            return 1;
        else
            return codigo4(n - 1) + codigo4(n - 2);
    }

    /**
     * Gerador de vetores aleatórios de tamanho pré-definido. 
     * @param tamanho Tamanho do vetor a ser criado.
     * @return Vetor com dados aleatórios, com valores entre 1 e (tamanho/2), desordenado.
     */
    static int[] gerarVetor(int tamanho){
        int[] vetor = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            vetor[i] = aleatorio.nextInt(1, tamanho/2);
        }
        return vetor;
        
    }

    static class Resultado {
        final int n;
        final long operacoes;
        final double tempoMs;

        Resultado(int n, long operacoes, double tempoMs) {
            this.n = n;
            this.operacoes = operacoes;
            this.tempoMs = tempoMs;
        }
    }

    static Resultado medirCodigo1(int n) {
        int[] vetor = gerarVetor(n);
        operacoes = 0;
        long inicio = System.nanoTime();
        int r = 0;
        for (int i = 0; i < vetor.length; i += 2) {
            operacoes++; // iteração
            r += vetor[i] % 2;
            operacoes += 2; // acesso e mod
        }
        long fim = System.nanoTime();
        double ms = (fim - inicio) * nanoToMilli;
        return new Resultado(n, operacoes, ms);
    }

    static Resultado medirCodigo2(int n) {
        int[] vetor = gerarVetor(n);
        operacoes = 0;
        long inicio = System.nanoTime();
        int contador = 0;
        for (int k = (vetor.length - 1); k > 0; k /= 2) {
            operacoes++; // loop externo
            for (int i = 0; i <= k; i++) {
                operacoes++; // cada iteração interna
                contador++;
            }
        }
        long fim = System.nanoTime();
        double ms = (fim - inicio) * nanoToMilli;
        return new Resultado(n, operacoes, ms);
    }

    static Resultado medirCodigo3(int n) {
        int[] vetor = gerarVetor(n);
        operacoes = 0;
        long inicio = System.nanoTime();
        for (int i = 0; i < vetor.length - 1; i++) {
            operacoes++; // loop externo
            int menor = i;
            for (int j = i + 1; j < vetor.length; j++) {
                operacoes++; // comparação interna
                if (vetor[j] < vetor[menor]) {
                    menor = j;
                    operacoes++; // atribuição menor
                }
            }
            int temp = vetor[i];
            vetor[i] = vetor[menor];
            vetor[menor] = temp;
            operacoes += 3; // troca
        }
        long fim = System.nanoTime();
        double ms = (fim - inicio) * nanoToMilli;
        return new Resultado(n, operacoes, ms);
    }

    static int codigo4Instrumentado(int n) {
        operacoes++;
        if (n <= 2)
            return 1;
        else
            return codigo4Instrumentado(n - 1) + codigo4Instrumentado(n - 2);
    }

    static Resultado medirCodigo4(int n) {
        operacoes = 0;
        long inicio = System.nanoTime();
        int resultado = codigo4Instrumentado(n);
        long fim = System.nanoTime();
        double ms = (fim - inicio) * nanoToMilli;
        return new Resultado(n, operacoes, ms);
    }

    static void executarSerie(String nome, String pacote, int[] tamanhos, java.util.function.IntFunction<Resultado> executor, StringBuilder csv) {
        System.out.println("\n" + nome + " (n, operacoes, ms)");
        for (int n : tamanhos) {
            try {
                Resultado r = executor.apply(n);
                System.out.printf("%d,%d,%.3f\n", r.n, r.operacoes, r.tempoMs);
                csv.append(String.format("%s,%s,%d,%d,%.3f\n", nome, pacote, r.n, r.operacoes, r.tempoMs));
            } catch (OutOfMemoryError oom) {
                System.out.printf("%d,OOM,0.000\n", n);
                csv.append(String.format("%s,%s,%d,OOM,0.000\n", nome, pacote, n));
                System.gc();
            } catch (StackOverflowError e) {
                System.out.printf("%d,SOF,0.000\n", n);
                csv.append(String.format("%s,%s,%d,SOF,0.000\n", nome, pacote, n));
            } catch (Throwable t) {
                System.out.printf("%d,ERROR,%s\n", n, t.toString());
                csv.append(String.format("%s,%s,%d,ERROR,%s\n", nome, pacote, n, t.toString()));
            }
        }
    }

    static class Ponto {
        final int n;
        final long operacoes;
        final double ms;

        Ponto(int n, long operacoes, double ms) {
            this.n = n;
            this.operacoes = operacoes;
            this.ms = ms;
        }
    }

    static void gerarGraficosHTML(String csvPath, String htmlPath) {
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(csvPath))) {
            String header = br.readLine();
            if (header == null) return;
            java.util.Map<String, java.util.List<Ponto>> dados = new java.util.LinkedHashMap<>();
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] f = line.split(",");
                if (f.length < 5) continue;
                String algo = f[0];
                String teste = f[1];
                String chave = algo + " - " + teste;
                int n = Integer.parseInt(f[2]);
                long op = 0;
                double ms = 0.0;
                try { op = Long.parseLong(f[3]); } catch (NumberFormatException e) {}
                try { ms = Double.parseDouble(f[4]); } catch (NumberFormatException e) {}
                dados.computeIfAbsent(chave, k -> new java.util.ArrayList<>()).add(new Ponto(n, op, ms));
            }

            StringBuilder html = new StringBuilder();
            html.append("<html><head><meta charset='UTF-8'><title>Graficos de desempenho</title></head><body>");
            html.append("<h1>Grafico de operacoes</h1>");
            html.append(gerarSVG(dados, true));
            html.append("<h1>Grafico de tempo (ms)</h1>");
            html.append(gerarSVG(dados, false));
            html.append("</body></html>");

            try (java.io.FileWriter fw = new java.io.FileWriter(htmlPath)) {
                fw.write(html.toString());
                System.out.println("Arquivo HTML gerado: " + htmlPath);
            }

        } catch (java.io.IOException ex) {
            System.err.println("Erro ao gerar graficos no HTML: " + ex.getMessage());
        }
    }

    static String gerarSVG(java.util.Map<String, java.util.List<Ponto>> dados, boolean usarOperacoes) {
        int w = 1100, h = 500, m = 60;
        long minN = Long.MAX_VALUE, maxN = Long.MIN_VALUE;
        double minY = Double.MAX_VALUE, maxY = Double.MIN_VALUE;
        for (java.util.List<Ponto> pts : dados.values()) {
            for (Ponto p : pts) {
                minN = Math.min(minN, p.n); maxN = Math.max(maxN, p.n);
                double y = usarOperacoes ? p.operacoes : p.ms;
                if (y > 0) {
                    minY = Math.min(minY, y);
                    maxY = Math.max(maxY, y);
                }
            }
        }
        if (minN >= maxN) { maxN = minN + 1; }
        if (minY >= maxY) { maxY = minY + 1; }

        StringBuilder b = new StringBuilder();
        b.append(String.format("<svg width='%d' height='%d' style='background:#fff;border:1px solid #888'>", w, h));

        for (String key : dados.keySet()) {
            java.util.List<Ponto> pts = new java.util.ArrayList<>(dados.get(key));
            pts.sort(java.util.Comparator.comparingInt(p -> p.n));
            StringBuilder poly = new StringBuilder();
            for (Ponto p : pts) {
                double x = m + (Math.log(p.n) - Math.log(minN)) / (Math.log(maxN) - Math.log(minN)) * (w - 2 * m);
                double val = usarOperacoes ? p.operacoes : p.ms;
                if (val <= 0) continue;
                double y = m + (1 - (Math.log(val) - Math.log(minY)) / (Math.log(maxY) - Math.log(minY))) * (h - 2*m);
                if (poly.length() > 0) poly.append(" ");
                poly.append(String.format("%.2f,%.2f", x, y));
            }
            String color = escolherCor(key);
            b.append(String.format("<polyline points='%s' fill='none' stroke='%s' stroke-width='2' />", poly.toString(), color));
            for (Ponto p : pts) {
                double x = m + (Math.log(p.n) - Math.log(minN)) / (Math.log(maxN) - Math.log(minN)) * (w - 2*m);
                double val = usarOperacoes ? p.operacoes : p.ms;
                if (val <= 0) continue;
                double y = m + (1 - (Math.log(val) - Math.log(minY)) / (Math.log(maxY) - Math.log(minY))) * (h - 2*m);
                b.append(String.format("<circle cx='%.2f' cy='%.2f' r='3' fill='%s' />", x, y, color));
            }
        }

        b.append(String.format("<line x1='%d' y1='%d' x2='%d' y2='%d' stroke='#000' />", m, h-m, w-m, h-m));
        b.append(String.format("<line x1='%d' y1='%d' x2='%d' y2='%d' stroke='#000' />", m, m, m, h-m));

        int idx=0;
        for (String key : dados.keySet()) {
            int y0 = m + idx*18;
            b.append(String.format("<rect x='%d' y='%d' width='12' height='12' fill='%s' />", w-220, y0, escolherCor(key)));
            b.append(String.format("<text x='%d' y='%d' font-size='12'>%s</text>", w-200, y0+11, key));
            idx++;
        }

        long minNLabel = minN;
        long maxNLabel = maxN;
        b.append(String.format("<text x='%d' y='%d' font-size='12'>n min= %d</text>", m, h-m+20, minNLabel));
        b.append(String.format("<text x='%d' y='%d' font-size='12'>n max= %d</text>", w-250, h-m+20, maxNLabel));

        b.append("</svg>");
        return b.toString();
    }

    static void gerarGraficosPNG(String csvPath, boolean usarOperacoes, String pathSaida) {
        java.util.Map<String, java.util.List<Ponto>> dados = new java.util.LinkedHashMap<>();
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(csvPath))) {
            String header = br.readLine();
            if (header == null) return;
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] f = line.split(",");
                if (f.length < 5) continue;
                String key = f[0] + " - " + f[1];
                int n = Integer.parseInt(f[2]);
                long op = 0;
                double ms = 0.0;
                try { op = Long.parseLong(f[3]); } catch (NumberFormatException e) {}
                try { ms = Double.parseDouble(f[4]); } catch (NumberFormatException e) {}
                dados.computeIfAbsent(key, k -> new java.util.ArrayList<>()).add(new Ponto(n, op, ms));
            }
        } catch (java.io.IOException ex) {
            System.err.println("Erro ao ler CSV para PNG: " + ex.getMessage());
            return;
        }

        int w = 1200, h = 700, m = 80;
        java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(w, h, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g = img.createGraphics();
        try {
            g.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(java.awt.Color.WHITE); g.fillRect(0,0,w,h);
            g.setColor(java.awt.Color.BLACK);
            g.drawRect(m, m, w-2*m, h-2*m);

            long minN = Long.MAX_VALUE, maxN = Long.MIN_VALUE;
            double minY = Double.MAX_VALUE, maxY = Double.MIN_VALUE;
            for (java.util.List<Ponto> pts : dados.values()) {
                for (Ponto p : pts) {
                    minN = Math.min(minN, p.n); maxN = Math.max(maxN, p.n);
                    double val = usarOperacoes ? p.operacoes : p.ms;
                    if (val > 0) {
                        minY = Math.min(minY, val);
                        maxY = Math.max(maxY, val);
                    }
                }
            }
            if (minN >= maxN) { maxN = minN + 1; }
            if (minY >= maxY) { maxY = minY + 1; }

            for (int i=0; i<=5; i++) {
                double fy = minY * Math.pow((maxY/minY), (double)i/5.0);
                int y = m + (int)((1 - (Math.log(fy)-Math.log(minY))/(Math.log(maxY)-Math.log(minY))) * (h-2*m));
                g.setColor(new java.awt.Color(220,220,220));
                g.drawLine(m, y, w-m, y);
                g.setColor(java.awt.Color.BLACK);
                g.drawString(String.format("%.2f", fy), 10, y+5);
            }

            int series = 0;
            for (String key : dados.keySet()) {
                java.util.List<Ponto> pts = new java.util.ArrayList<>(dados.get(key));
                pts.sort(java.util.Comparator.comparingInt(p -> p.n));
                java.awt.Color c = java.awt.Color.decode(escolherCor(key));
                g.setColor(c);
                java.awt.Polygon polygon = new java.awt.Polygon();
                for (Ponto p : pts) {
                    double val = usarOperacoes ? p.operacoes : p.ms;
                    if (val <= 0) continue;
                    int x = m + (int)((Math.log(p.n)-Math.log(minN))/(Math.log(maxN)-Math.log(minN))*(w-2*m));
                    int y = m + (int)((1-(Math.log(val)-Math.log(minY))/(Math.log(maxY)-Math.log(minY)))*(h-2*m));
                    if (polygon.npoints>0) g.drawLine(polygon.xpoints[polygon.npoints-1], polygon.ypoints[polygon.npoints-1], x, y);
                    polygon.addPoint(x, y);
                    g.fillOval(x-3, y-3, 6, 6);
                }
                g.drawString(key, w-m-250, m + 20 + 18*series);
                series++;
            }

            g.drawString(usarOperacoes ? "Operações" : "Tempo (ms)", m, m-30);
            g.drawString("n (escala log)", w/2-40, h-m+40);

            javax.imageio.ImageIO.write(img, "png", new java.io.File(pathSaida));
            System.out.println("Arquivo PNG gerado: " + pathSaida);
        } catch (java.io.IOException ex) {
            System.err.println("Erro ao gravar PNG: " + ex.getMessage());
        } finally {
            g.dispose();
        }
    }

    static String escolherCor(String chave) {
        String[] cores = {"#1f77b4", "#ff7f0e", "#2ca02c", "#d62728", "#9467bd", "#8c564b"};
        return cores[Math.abs(chave.hashCode()) % cores.length];
    }

    public static void main(String[] args) {
        System.out.println("Contagem de operacoes + tempo (ms)");

        StringBuilder csv = new StringBuilder();
        csv.append("algoritmo,teste,n,operacoes,ms\n");

        executarSerie("Algoritmo 1", "Teste Grande", tamanhosTesteGrande, App::medirCodigo1, csv);
        executarSerie("Algoritmo 2", "Teste Grande", tamanhosTesteGrande, App::medirCodigo2, csv);
        executarSerie("Algoritmo 3", "Teste Medio", tamanhosTesteMedio, App::medirCodigo3, csv);
        executarSerie("Algoritmo 4", "Teste Pequeno", tamanhosTestePequeno, App::medirCodigo4, csv);

        try (java.io.FileWriter writer = new java.io.FileWriter("resultado.csv")) {
            writer.write(csv.toString());
            System.out.println("\nArquivo CSV gerado: resultado.csv");
        } catch (java.io.IOException ex) {
            System.err.println("Erro ao escrever resultado.csv: " + ex.getMessage());
        }

        gerarGraficosHTML("resultado.csv", "graficos.html");
        gerarGraficosPNG("resultado.csv", true, "graficos_operacoes.png");
        gerarGraficosPNG("resultado.csv", false, "graficos_tempo.png");

        System.out.println("\nTerminado. Copie/cole os dados para planilha para criar graficos e comparar desempenho esperado vs. obtido.");
    }
}
