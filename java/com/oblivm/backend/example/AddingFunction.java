package com.oblivm.backend.example;

import com.oblivm.backend.circuits.arithmetic.IntegerLib;
import com.oblivm.backend.flexsc.CompEnv;
import com.oblivm.backend.gc.BadLabelException;
import com.oblivm.backend.util.EvaRunnable;
import com.oblivm.backend.util.GenRunnable;
import com.oblivm.backend.util.Utils;

import java.math.BigInteger;

public class AddingFunction {

    static int a1 = 1;
    static int a2 = 1;
    static int a3 = 1;
    static int a4 = 1;

    static public <T> T[] compute(CompEnv<T> gen, T[] inputA, T[] inputB, T[] inputC, T[] inputD) {
        IntegerLib<T> lib = new IntegerLib<T>(gen);
        return lib.add(lib.multiply(inputA, inputC), lib.multiply(inputB, inputD));
    }

    private static int bytesToInt(byte[] src) {
        int value = 0;
        for (int i = 0; i < src.length; i++) {
            if (src[i] == 1) value += Math.pow(2, i);
        }
        return value;
    }

    private static byte[] convertBool2Byte(boolean[] booleanArray) {
        byte[] byteResult = new byte[32];
//            System.out.println(eva.outputToBob(scResult));
        for (int i = 0; i < booleanArray.length; i++) {
            byte b;
            if (booleanArray[i]) {
                b = 1;
            } else {
                b = 0;
            }
//                byteResult[result.length - i - 1] = b;
            byteResult[i] = b;
        }
        return byteResult;
    }

    public static class Generator<T> extends GenRunnable<T> {

        T[] inputA;
        T[] inputB;
        T[] inputC;
        T[] inputD;
        T[] scResult;

        @Override
        public void prepareInput(CompEnv<T> gen) {
            int x1 = Integer.parseInt(args[1]);
            int x2 = Integer.parseInt(args[2]);
            inputA = gen.inputOfAlice(Utils.fromInt(new Integer(x1), 32));
            inputB = gen.inputOfAlice(Utils.fromInt(new Integer(x2), 32));
            gen.flush();
            inputC = gen.inputOfBob(new boolean[32]);
            inputD = gen.inputOfBob(new boolean[32]);
        }

        @Override
        public void secureCompute(CompEnv<T> gen) {

            scResult = compute(gen, inputA, inputB, inputC, inputD);
        }

        @Override
        public void prepareOutput(CompEnv<T> gen) throws BadLabelException {

            gen.outputToBob(scResult);
//            System.out.println(gen.outputToAlice(scResult));

            boolean[] result = gen.outputToAlice(scResult);
            byte[] byteResult = convertBool2Byte(result);
            for (int i = 0; i < byteResult.length; i++) {
                System.out.print(byteResult[i]);
            }
            System.out.println();

            int finalResult = bytesToInt(byteResult);
            AddingGenerator.output = finalResult;

//            System.out.println(eva.outputToBob(scResult));
            for (int i = 0; i < result.length; i++) {
                System.out.print(result[i] ? 1 : 0);
            }
            System.out.println();
        }
    }

    public static class Evaluator<T> extends EvaRunnable<T> {
        T[] inputA;
        T[] inputB;
        T[] inputC;
        T[] inputD;
        T[] scResult;

        @Override
        public void prepareInput(CompEnv<T> eva) {
            int x1 = Integer.parseInt(args[1]);
            int x2 = Integer.parseInt(args[2]);
            inputA = eva.inputOfAlice(new boolean[32]);
            inputB = eva.inputOfAlice(new boolean[32]);
            eva.flush();
            inputC = eva.inputOfBob(Utils.fromInt(new Integer(x1), 32));
            inputD = eva.inputOfBob(Utils.fromInt(new Integer(x2), 32));
        }

        @Override
        public void secureCompute(CompEnv<T> eva) {

            scResult = compute(eva, inputA, inputB, inputC, inputD);
        }

        @Override
        public void prepareOutput(CompEnv<T> eva) throws BadLabelException {
            eva.outputToAlice(scResult);
            boolean[] result = eva.outputToBob(scResult);
            byte[] byteResult = convertBool2Byte(result);

            for (int i = 0; i < byteResult.length; i++) {
                System.out.print(byteResult[i]);
            }
            System.out.println();

            int finalResult = bytesToInt(byteResult);
            AddingEvaluator.output = finalResult;
            System.out.println(finalResult);
        }
    }
}
