package com.openca.uni.continous;

import com.openca.utils.DecimalRounder;

import java.util.Arrays;


/** Autómata Celular Unidimensional Contínuo. Regla Total
 * 
 * @author juanky
 *
 */
public final class Automata1DTotalContinous extends Automata1DContinous
{

	/** 
	 * factor a aplicar al valor del vecindario.
	 */
	double reglafactor;
	
	/**
	 * offset a sumar al valor del vecindario 
	 */
	double reglaoffset;

	/** Constructor
	 * 
	 */
	public Automata1DTotalContinous()
	{
		resetMetricas();
	}

	/**
	 * 
	 * @param celulas Matriz con las células iniciales del autómata.
	 */
	public Automata1DTotalContinous(double[] celulas)
	{
		super(celulas);
	}

	@Override
	protected void evolucionarCelula(int indice)
	{
		final double value = setVecinos(indice)*reglafactor + reglaoffset;
		if((int)value>=estados-1)
		{
			celulasnext[indice] = value - (int)value;
		}
		else
		{
			celulasnext[indice] = value;
		}

	}


	@Override
	public final String getMaxRule() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final void setRadious(int radio)
	{
		super.setRadious(radio);
	}

	@Override
	public final void setRule(String regla)
	{
		final String[] reg = new String[2];
		Arrays.fill(reg,"");

		int index = 0;

		for(int i = 0; i < regla.length(); i++)
		{
			if(regla.charAt(i) == '-')
			{
				if(i+1 < regla.length())
				{
					index++;
				}
			}
			else
			{
				reg[index] = reg[index].concat(new String((new Character(regla.charAt(i))).toString()));
			}
		}

		reglafactor = Double.parseDouble(reg[0])/implicadas;
		reglaoffset = Double.parseDouble(reg[1]);

	}

	/** Calcula el valor del vecindario de una célula
	 * 
	 * @param indice Indice de la célula central del vecindario
	 * @return Índice de la lookUpTable que se usará para acceder a la célula
	 */
	protected final double setVecinos(int indice)
	{
		int nuevax;
		double code=0;
		if(indice == 0)
		{
			for(int i = -radio; i <=radio; i++)
			{
				nuevax = getRelativeIndex(i);
				code +=  celulas[nuevax];
				code = DecimalRounder.round4(code);

			}
		}
		else
		{
			nuevax = getRelativeIndex(indice - radio - 1);
			final double bres =  celulas[nuevax];
			nuevax = getRelativeIndex(indice + radio);
			final double bsum =  celulas[nuevax];
			code = DecimalRounder.round4(vecinos[indice-1]-DecimalRounder.round4(bres) + DecimalRounder.round4(bsum)) ;
		}
		vecinos[indice] = code;
		return code;
	}



}
