package com.oblivm.backend.example;

import com.oblivm.backend.circuits.arithmetic.IntegerLib;
import com.oblivm.backend.flexsc.CompEnv;
import com.oblivm.backend.gc.BadLabelException;
import com.oblivm.backend.util.EvaRunnable;
import com.oblivm.backend.util.GenRunnable;

public class HammingDistance {

	static public<T> T[] compute(CompEnv<T> gen, T[] inputA, T[] inputB){
		return  new IntegerLib<T>(gen).hammingDistance(inputA, inputB);
	}


	public static class Generator<T> extends GenRunnable<T> {

		T[] inputA;
		T[] inputB;
		T[] scResult;

		@Override
		public void prepareInput(CompEnv<T> gen) {
			boolean[] in = new boolean[10000];
			for(int i = 0; i < in.length; ++i)
				in[i] = CompEnv.rnd.nextBoolean();
			inputA = gen.inputOfAlice(in);
			gen.flush();
			inputB = gen.inputOfBob(new boolean[10000]);
		}

		@Override
		public void secureCompute(CompEnv<T> gen) {
			scResult = compute(gen, inputA, inputB);
		}
		@Override
		public void prepareOutput(CompEnv<T> gen) throws BadLabelException {
			System.out.println(gen.outputToAlice(scResult));
			gen.outputToBob(scResult);
		}

	}

	public static class Evaluator<T> extends EvaRunnable<T> {
		T[] inputA;
		T[] inputB;
		T[] scResult;

		@Override
		public void prepareInput(CompEnv<T> eva) {
			boolean[] in = new boolean[10000];
			for(int i = 0; i < in.length; ++i)
				in[i] = CompEnv.rnd.nextBoolean();
			inputA = eva.inputOfAlice(new boolean[10000]);
			eva.flush();
			inputB = eva.inputOfBob(in);
		}

		@Override
		public void secureCompute(CompEnv<T> eva) {
			scResult = compute(eva, inputA, inputB);
		}

		@Override
		public void prepareOutput(CompEnv<T> eva) throws BadLabelException {
			eva.outputToAlice(scResult);
			System.out.println(eva.outputToBob(scResult));
		}
	}
}
