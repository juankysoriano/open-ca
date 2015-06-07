package com.openca.bi.discrete;

import com.openca.bi.Automata2D;

import java.util.Random;


/** Autómata Celular Bidimensional Discreto
 * 
 * @author juanky
 *
 */
public abstract class Automata2DDiscrete extends Automata2D implements IAutomata2DDiscrete
{
	/** Matriz de células del autómata */
	protected byte[][] celulas; 
	/** Matriz de células del autómata tras evolve */
	protected byte[][] celulasnext;
	/** Matriz con el valor del vecindario calculado para cada célula */
	protected int[][] vecinos;

	/** Constructor
	 * 
	 */
	public Automata2DDiscrete()
	{

	}

	/** Constructor
	 * 
	 * @param celulas Matriz con el estado inicial del autómata.
	 */
	public Automata2DDiscrete(byte[][] celulas)
	{
		super();
		this.celulas = celulas;
	}

	@Override
	public final void RandomIC() //Calcula una Configuración inicial aleatoria
	{
		final Random rand = new Random();
		resetMetricas();
		vivas = 0;

		final int simetrico = rand.nextInt(2);
		final int densidad = rand.nextInt(101);

		if(simetrico==0)
		{
			for(int i = 0; i < tamano; i++)
			{
				for(int j = 0; j < tamano; j++)
				{
					celulas[i][j] = 0;
					final int numero = rand.nextInt(101);
					if(numero<densidad)
					{
						final byte estado = (byte) rand.nextInt(estados);
						celulas[i][j] = estado;
						estadosvivas[estado]++;
						if(estado > 0)
						{
							vivas++;
						}
					}
				}

			}
		}
		else
		{
			for(int i = 0; i < tamano/2; i++)
			{
				for(int j = 0; j < tamano/2; j++)
				{
					celulas[i][j] = 0;
					celulas[i][tamano-1-j] = 0;
					celulas[tamano-1-i][j] = 0;
					celulas[tamano-1-i][tamano-1-j] = 0;
					final int numero = rand.nextInt(101);
					if(numero<densidad)
					{
						final byte estado = (byte) rand.nextInt(estados);
						celulas[i][j] = estado;
						celulas[i][tamano-1-j] = estado;
						celulas[tamano-1-i][j] = estado;
						celulas[tamano-1-i][tamano-1-j] = estado;

						estadosvivas[estado]++;
						estadosvivas[estado]++;
						estadosvivas[estado]++;
						estadosvivas[estado]++;

						if(estado > 0)
						{
							vivas++;
							vivas++;
							vivas++;
							vivas++;
						}
					}
				}

			}
		}
		for(int i = 0; i < getNumStates(); i++)
		{
			estadodiferenciashamming[i] = getAliveState(i);
			estadodiferenciaslee[i] = getAliveState(i);
			estadodiferenciaspotencial[i] = getAliveState(i);
		}

	}

	@Override
	public void evolve()
	{ //Evolucionamos todas las clulas
		resetMetricas();
		for(int i = 0; i < tamano; i++)
		{
			for(int j =0; j < tamano; j++)
			{
				evolucionarCelula(i,j);
				updateMetricas(i,celulasnext[i][j], celulas[i][j]);

			}
		}

		final byte[][] cellsaux = celulasnext;
		celulasnext = celulas;
		celulas = cellsaux;

	}

	@Override
	public  final void evolucionar(byte[][] celulasinit)
	{ //Evolucionamos todas las clulas
		resetMetricas();

		for(int i = 0; i < tamano; i++)
		{
			for(int j =0; j < tamano; j++)
			{
				evolucionarCelula(i,j);
				updateMetricas(i,celulasnext[i][j], celulasinit[i][j]);
			}
		}

		final byte[][] cellsaux = celulasnext;
		celulasnext = celulas;
		celulas = cellsaux;
	}

	@Override
	public  byte[][] getCelulas()
	{
		return celulas;
	}

	@Override
	public int getHammingDiffState(int estado)
	{
		return estadodiferenciashamming[estado];
	}


	@Override
	public int getDiffLeeState(int estado)
	{
		return estadodiferenciaslee[estado];
	}

	@Override
	public int getDeltaPotState(int estado)
	{
		return estadodiferenciaspotencial[estado];
	}

	@Override
	public int getAliveState(int estado)
	{
		return estadosvivas[estado];
	}

	@Override
	public final void clean()
	{
		resetMetricas();

		vivas = 9;
		estadosvivas[1]=9;

		for(int i = 0; i < celulas.length; i++)
		{
			for(int j = 0; j < celulas.length; j++)
			{
				celulas[i][j] = 0;
			}
		}


		final int mitad = (int)Math.floor(getSize()/2);

		getCelulas()[mitad][mitad] = 1;
		getCelulas()[mitad - 1][mitad - 1]= 1;
		getCelulas()[mitad - 1][mitad]= 1;
		getCelulas()[mitad][mitad - 1]= 1;
		getCelulas()[mitad + 1][mitad]= 1;
		getCelulas()[mitad+1][mitad + 1]= 1;
		getCelulas()[mitad + 1][mitad - 1]= 1;
		getCelulas()[mitad - 1][mitad + 1]= 1;
		getCelulas()[mitad][mitad+1]= 1;


		for(int i = 0; i < getNumStates(); i++)
		{
			estadodiferenciashamming[i] = getAliveState(i);
			estadodiferenciaslee[i] = getAliveState(i);
			estadodiferenciaspotencial[i] = getAliveState(i);
		}
	}

	@Override
	public void setCelulas(byte[][] celulas)
	{
		if((celulas != null) && (celulas.length == tamano))
		{
			this.celulas = celulas;
		}
		else
		{
			try
			{
				throw new Exception("Distintos tamaños para las células");
			}
			catch (final Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public  void setCelulas(double[][] celulalist)
	{
		if((celulalist != null) && (celulalist.length == tamano))
		{
			for(int i = 0 ; i < tamano; i++)
			{
				for(int j = 0; j < tamano; j++)
				{
					celulas[i][j] = (byte) Math.abs(celulalist[i][j]);
					if(celulas[i][j]==estados)
					{
						celulas[i][j]=(byte) (estados-1);
					}
				}
			}
		}
		else
		{
			try
			{
				throw new Exception("Distintos tamaños para las células");
			}
			catch (final Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void setSize(int tamano)
	{
		if(this.tamano != tamano)
		{
			this.tamano = tamano;
			celulas = new byte[this.tamano][this.tamano];
			celulasnext = new byte[this.tamano][this.tamano];
			vecinos = new int[this.tamano][this.tamano];
		}
	}

	@Override
	public void updateAlive()
	{
		vivas = 0;
		resetMetricas();
		for(int i = 0; i < tamano; i++)
		{
			for(int j = 0; j < tamano; j++)
			{
				estadosvivas[celulas[i][j]]++;
				if(celulas[i][j]>0)
				{
					vivas++;
				}
			}
		}
	}
}
