package com.openca.bi.continous;

import com.openca.utils.DecimalRounder;

import java.util.Arrays;


/** Autómata Celular Bidimensional Contínuo. Regla Total
 * 
 * @author juanky
 *
 */
public final class Automata2DTotalisticContinousMoore extends Automata2DContinous
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
	public Automata2DTotalisticContinousMoore()
	{
		super();
	}

	/** Constructor
	 * 
	 * @param celulas Matriz con las células iniciales del autómata.
	 */
	public Automata2DTotalisticContinousMoore(double[][] celulas)
	{
		super(celulas);
		// TODO Auto-generated constructor stub
	}



	@Override
	protected void evolucionarCelula(int x, int y)
	{	//this.celulasnext[x][y] = new Celula();

		final double value = setVecinos(x,y)*reglafactor + reglaoffset;
		if((int)value>=estados-1)
		{
			celulasnext[x][y] = value - (int)value;
		}
		else
		{
			celulasnext[x][y] = value;
		}


	}

	@Override
	public String getMaxRule() {
		// TODO Auto-generated method stub
		return null;
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

		reglafactor = Double.parseDouble(reg[0])/Math.pow(implicadas, 2);

		reglaoffset = Double.parseDouble(reg[1]);

	}


	/** Calcula el valor del vecindario de una célula(Moore)
	 * 
	 * @param x Coordenada x de la célula central
	 * @param y Coordenada y de la célula central
	 * @return Índice de la lookUpTable que se usará para acceder a la célula
	 */
	protected final double setVecinos(int x, int y) //Calcula el vecindario del que forma parte una clula (Moore)
	{
		int nuevax;
		int nuevay;
		int nuevax1;
		int nuevay1;
		double code;

		if(y!= 0)
		{
			nuevay1 = getRelativeIndex(y - radio - 1);
			nuevay = getRelativeIndex(y + radio);
			code =  vecinos[x][y-1];
			for(int i = - radio; i <= radio; i++)
			{
				nuevax = getRelativeIndex(x + i);
				code+= DecimalRounder.round4(DecimalRounder.round4(celulas[nuevax][nuevay]) - DecimalRounder.round4(celulas[nuevax][nuevay1]));
				code = DecimalRounder.round4(code);
			}
		}
		else if((y == 0) && (x!=0))
		{
			nuevax1 = getRelativeIndex(x - radio - 1);
			nuevax = getRelativeIndex(x + radio);
			code =  vecinos[x-1][y];
			for(int i = - radio; i <= radio; i++)
			{
				nuevay = getRelativeIndex(i);
				code+= DecimalRounder.round4(DecimalRounder.round4(celulas[nuevax][nuevay]) - DecimalRounder.round4(celulas[nuevax1][nuevay]));
				code = DecimalRounder.round4(code);

			}
			//+ bsum - bres;

		}
		else //if x== && y == 0
		{
			code=0;
			for(int i = -radio; i <= radio; i++)
			{
				nuevay = getRelativeIndex(i);
				for(int j = -radio; j <= radio; j++)
				{
					nuevax = getRelativeIndex(j);
					code += celulas[nuevax][nuevay];
					code = DecimalRounder.round4(code);
				}
			}
		}
		vecinos[x][y] = code;
		return code;
	}
}
