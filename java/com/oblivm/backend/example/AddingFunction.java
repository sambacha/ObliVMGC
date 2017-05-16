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

    static public <T> T[] compute(CompEnv<T> gen, T[] inputA, T[] inputB) {
        return new IntegerLib<T>(gen).add(inputA, inputB);
    }

    private static int bytesToInt(byte[] src, int offset) {
        int value;
        value = (int) ((src[offset] & 0xFF)
                | ((src[offset + 1] & 0xFF) << 8)
                | ((src[offset + 2] & 0xFF) << 16)
                | ((src[offset + 3] & 0xFF) << 24));
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
        T[] scResult;

        @Override
        public void prepareInput(CompEnv<T> gen) {
            int x1 = Integer.parseInt(args[1]) * a1;
            int x2 = Integer.parseInt(args[2]) * a2;
            inputA = gen.inputOfAlice(Utils.fromInt(new Integer(x1 + x2), 32));
            gen.flush();
            inputB = gen.inputOfBob(new boolean[32]);
        }

        @Override
        public void secureCompute(CompEnv<T> gen) {

            scResult = compute(gen, inputA, inputB);
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

            int finalResult = bytesToInt(byteResult, 0);
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
        T[] scResult;

        @Override
        public void prepareInput(CompEnv<T> eva) {
            int x1 = Integer.parseInt(args[1]) * a3;
            int x2 = Integer.parseInt(args[2]) * a4;
            inputA = eva.inputOfAlice(new boolean[32]);
            eva.flush();
            inputB = eva.inputOfBob(Utils.fromInt(new Integer(x1 + x2), 32));
        }

        @Override
        public void secureCompute(CompEnv<T> eva) {

            scResult = compute(eva, inputA, inputB);
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

            int finalResult = bytesToInt(byteResult, 0);
            AddingEvaluator.output = finalResult;
            System.out.println(finalResult);
        }
    }
}
