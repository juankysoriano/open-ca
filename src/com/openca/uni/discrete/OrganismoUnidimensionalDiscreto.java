package com.openca.uni.discrete;

import com.openca.uni.Automata1D;

import java.util.Random;


/** Autómata Celular Unidimensional Discreto
 * 
 * @author juanky
 *
 */
public abstract class OrganismoUnidimensionalDiscreto extends Automata1D implements IAutomata1DDiscreto
{
	/** Matriz de células del autómata */
	protected byte[] celulas; 
	/** Matriz de células del autómata tras evolve */
	protected byte[] celulasnext;
	/** Matriz con el valor del vecindario calculado para cada célula */
	protected int[] vecinos;

	/** Constructor
	 * 
	 */
	public OrganismoUnidimensionalDiscreto()
	{

	}

	/** Constructor
	 * 
	 * @param celulas Lista de celulas del estado inicial del autómata.
	 */
	public OrganismoUnidimensionalDiscreto(byte[] celulas)
	{
		super();
		this.celulas = celulas;
	}


	@Override
	public void RandomIC()//Calcula una Configuraci�n inicial aleatoria
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
				celulas[i] = 0;
				final int numero = rand.nextInt(101);
				if(numero<densidad)
				{
					final byte estado = (byte) rand.nextInt(estados);
					celulas[i] = estado;
					estadosvivas[estado]++;
					if(estado != 0)
					{
						vivas++;
					}
				}
			}
		}
		else
		{
			for(int i = 0; i < tamano/2; i++)
			{
				celulas[i] = 0;
				celulas[tamano-1-i]=0;
				final int numero = rand.nextInt(101);
				if(numero<densidad)
				{
					final byte estado = (byte) rand.nextInt(estados);
					celulas[i] = estado;
					celulas[tamano-1-i] = estado;
					estadosvivas[estado]++;
					estadosvivas[estado]++;
					if(estado != 0)
					{
						vivas++;
						vivas++;
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
	{
		resetMetricas();
		for(int i = 0; i < tamano; i++)
		{
			evolucionarCelula(i);
			updateMetricas(i,celulasnext[i], celulas[i]);
		}

		final byte[] cellsaux = celulasnext;
		celulasnext = celulas;
		celulas = cellsaux;
	}


	@Override
	public final void evolucionar(byte[] celulasinit)
	{
		resetMetricas();
		for(int i = 0; i < tamano; i++)
		{
			evolucionarCelula(i);
			updateMetricas(i,celulasnext[i], celulasinit[i]);
		}

		final byte[] cellsaux = celulasnext;
		celulasnext = celulas;
		celulas = cellsaux;
	}

	@Override
	public final byte[] getCelulas()
	{
		return celulas;
	}


	@Override
	public void clean()
	{
		resetMetricas();
		estadosvivas[1] = 1;
		vivas = 1;
		for(int i = 0; i < celulas.length; i++)
		{
			celulas[i] = 0;
		}
		celulas[(int)Math.floor(getSize()/2)] = 1;

		for(int i = 0; i < getNumStates(); i++)
		{
			estadodiferenciashamming[i] = getAliveState(i);
			estadodiferenciaslee[i] = getAliveState(i);
			estadodiferenciaspotencial[i] = getAliveState(i);
		}
	}

	@Override
	public final void setCells(byte[] celulalist)
	{
		if((celulalist != null) && (celulalist.length == tamano))
		{
			celulas = celulalist;
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
	public final void setCells(double[] celulalist)
	{
		if(celulalist.length == tamano)
		{
			for(int i = 0 ; i < tamano; i++)
			{
				celulas[i] = (byte) Math.abs(celulalist[i]);
				if(celulas[i]==estados)
				{
					celulas[i]=(byte) (estados-1);
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
			celulas = new byte[this.tamano];
			celulasnext = new byte[this.tamano];
			vecinos = new int[this.tamano];
		}
	}

	@Override
	public void updateAlive()
	{
		vivas = 0;
		resetMetricas();

		for(int i = 0; i < tamano; i++)
		{
			estadosvivas[celulas[i]]++;
			if(celulas[i]>0)
			{
				vivas++;
			}
		}
	}
}
