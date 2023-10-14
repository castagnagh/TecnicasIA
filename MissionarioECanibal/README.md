using System;

namespace MultiplicacaoSimples
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.Write("Digite o primeiro valor: ");
            double valor1 = Convert.ToDouble(Console.ReadLine());

            Console.Write("Digite o segundo valor: ");
            double valor2 = Convert.ToDouble(Console.ReadLine());

            double resultado = valor1 * valor2;

            Console.WriteLine("O resultado da multiplicação é: " + resultado);
        }
    }
}
