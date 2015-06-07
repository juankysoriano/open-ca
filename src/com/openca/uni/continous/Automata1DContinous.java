package com.openca.uni.continous;

import com.openca.uni.Automata1D;

import java.util.Random;

/** Autómata Celular Unidimensional Contínuo
 * 
 * @author juanky
 *
 */
public abstract class Automata1DContinous extends Automata1D implements IAutomata1DContinous
{
	/** Matriz de células del autómata */
	protected double[] celulas; 
	/** Matriz de células del autómata tras evolve */
	protected double[] celulasnext;
	/** Matriz con el valor del vecindario calculado para cada célula */
	protected double[] vecinos;

	protected double[] estadosvivas;
	/** Diferencia hamming para cada posible valor de célula */
	protected double[] estadodiferenciashamming;
	/** Diferencia lee para cada posible valor de célula */
	protected double[] estadodiferenciaslee;
	/** Diferencia potencial para cada posible valor de célula */
	protected double[] estadodiferenciaspotencial;
	/** Diferencia hamming para un sector */
	protected double[] sectordiferenciashamming;
	/** Diferencia lee para cada posible sector */
	protected double[] sectordiferenciaslee;
	/** Diferecia potencial para cada posible sector */
	protected double[] sectordiferenciaspotencial;
	/** Número de vivas de cada posible sector */
	protected double[] sectorvivas;


	/** Constructor
	 * 
	 */
	public Automata1DContinous()
	{
		resetMetricas();
	}

	/** Constructor
	 * 
	 * @param celulas Lista de celulas del estado inicial del autómata.
	 */
	public Automata1DContinous(double[] celulas)
	{
		super();
		this.celulas = celulas;
		resetMetricas();
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
				if(numero < densidad)
				{
					final double estado = rand.nextDouble() +rand.nextInt(estados-1);
					celulas[i] = estado;
					final int state = (int) Math.round(celulas[i]);
					estadosvivas[state]++;;
					if(state>0)
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
				celulas[i] = 0

				;
				celulas[tamano-i-1] = 0;
				final int numero = rand.nextInt(101);
				if(numero < densidad)
				{
					final double estado = rand.nextDouble() +rand.nextInt(estados-1);
					celulas[i] = estado;
					celulas[tamano-i-1] = estado;
					final int state = (int) Math.round(celulas[i]);
					estadosvivas[state]++;
					estadosvivas[state]++;

					if(state>0)
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
	public  void evolve()
	{
		resetMetricas();
		for(int i = 0; i < tamano; i++)
		{
			evolucionarCelula(i);
			updateMetricas(i,celulasnext[i], celulas[i]);

		}

		final double[] cellsaux = celulasnext;
		celulasnext = celulas;
		celulas = cellsaux;
	}

	public void evolve(double celulasinit[])
	{
		resetMetricas();
		for(int i = 0; i < tamano; i++)
		{
			evolucionarCelula(i);
			updateMetricas(i,celulasnext[i], celulasinit[i]);
		}
		final double[] cellsaux = celulasnext;
		celulasnext = celulas;
		celulas = cellsaux;
	}

	@Override
	public  double[] getCells()
	{
		return celulas;
	}

	@Override
	public int getHammingDiffState(int i)
	{
		return (int)estadodiferenciashamming[i];
	}

	@Override
	public int getHammingDiffSector(int sector)
	{
		return (int) sectordiferenciashamming[sector];
	}

	@Override
	public int getDiffLeeState(int i)
	{
		return (int)estadodiferenciaslee[i];
	}

	@Override
	public int getDiffLeeSector(int sector)
	{
		return (int) sectordiferenciaslee[sector];
	}

	@Override
	public int getDeltaPotState(int i)
	{
		return (int)estadodiferenciaspotencial[i];
	}

	@Override
	public int getDeltaPotSector(int sector)
	{
		return (int) sectordiferenciaspotencial[sector];
	}

	protected double[] getVecinos()
	{
		return vecinos;
	}

	@Override
	public int getAliveState(int i)
	{
		return (int)estadosvivas[i];
	}


	@Override
	public int getAliveSector(int sector)
	{
		return (int) sectorvivas[sector];
	}

	@Override
	public void clean()
	{
		estadosvivas[1] = 1;
		vivas = 1;
		for(int i = 0; i < celulas.length; i++)
		{
			celulas[i] = 0;
		}
		celulas[(int)Math.floor(getSize()/2)] = 1;


	}

	@Override
	public  void setCells(byte[] celulalist)
	{
		if((celulalist != null) && (celulalist.length == tamano))
		{
			for(int i = 0; i < tamano; i++)
			{
				celulas[i] = (celulalist[i]);
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
	public  void setCells(double[] celulalist)
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
	public void setStates(byte estados)
	{
		this.estados = estados;
		estadosvivas = new double[estados];
		estadodiferenciashamming = new double[estados];
		estadodiferenciaslee = new double[estados];
		estadodiferenciaspotencial = new double[estados];

	}

	@Override
	public void setSectors(byte secciones)
	{
		this.secciones = secciones;
		sectordiferenciashamming = new double[secciones];
		sectordiferenciaslee = new double[secciones];
		sectordiferenciaspotencial = new double[secciones];
		sectorvivas = new double[secciones];
	}

	@Override
	public void setSize(int tamano)
	{
		if(this.tamano != tamano)
		{
			this.tamano = tamano;
			celulas = new double[this.tamano];
			celulasnext = new double[this.tamano];
			vecinos = new double[this.tamano];
		}
	}

	/**  Actualiza el valor de las métricas.
	 * 
	 * @param index Índice de la célula.
	 * @param estado Valor actual de la célula.
	 * @param estadoanterior Valor anterior de la célula.
	 */
	protected void updateMetricas(int index, double estado, double estadoanterior)
	{
		final int sector = (int) (index/((double) getSize()/ getNumSections()));
		double diferencia = (estado-estadoanterior);
		final double intensidad = estado - (int)estado;
		int state = (int)estado +1;
		if(state== getNumStates())
		{
			state--;
		}
		estadodiferenciaspotencial[state] = estadodiferenciaspotencial[state] + diferencia;

		if(estadoanterior!=0)
		{
			sectordiferenciaspotencial[sector] = sectordiferenciaspotencial[sector] + diferencia;
		}

		estadosvivas[state]+= intensidad;
		sectorvivas[sector] = sectorvivas[sector]+intensidad;

		if(estadoanterior!=estado)
		{
			diferencia = Math.abs(diferencia);
			sectordiferenciashamming[sector] = sectordiferenciashamming[sector] + diferencia;
			estadodiferenciashamming[state] = estadodiferenciashamming[state] + diferencia;
			estadodiferenciaslee[state] = estadodiferenciaslee[state] + Math.min(diferencia, (estados-1)-diferencia);
			sectordiferenciaslee[sector] = sectordiferenciaslee[sector] + Math.min(diferencia, (estados-1)-diferencia);
		}
	}

	@Override
	protected void resetMetricas()
	{
		for(int i = 0; i < getNumStates(); i++)
		{
			estadodiferenciashamming[i]=0;
			estadodiferenciaslee[i]=0;
			estadosvivas[i]=0;
		}

		for(int i = 0; i < getNumSections(); i++)
		{
			sectordiferenciashamming[i]=0;
			sectordiferenciaslee[i]=0;
			sectorvivas[i]=0;
		}
	}
	
	@Override
	public void updateAlive()
	{
		vivas = 0;
		resetMetricas();

		for(int i = 0; i < tamano; i++)
		{
			final int state = (int) Math.round(celulas[i]);
			estadosvivas[state]++;
			if(celulas[i]>0)
			{
				vivas++;
			}
		}
	}

}
