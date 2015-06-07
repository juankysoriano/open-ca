package com.openca.uni.discrete;

/** Autómata Celular Unidimensional de tipo Cíclico con vecindad de Moore
 * 
 * @author juanky
 *
 */
public class Automata1DCiclico extends OrganismoUnidimensionalDiscreto
{
	/**  Regla (Valor del vecindario a superar para cambiar estado de una célula) */
	private short umbral;
	
	/** Constructor 
	 * 
	 */
	public Automata1DCiclico()
	{
		super();
	}

	/** Constructor 
	 * 
	 * @param celulas Lista de células de configuración inicial.
	 */
	public Automata1DCiclico(byte[] celulas) {
		super(celulas);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void evolucionarCelula(int indice)
	{
		final byte cell = celulas[indice];
		byte cellnext = cell;


		int contar = 0;
		final byte estado = celulas[indice];
		if(estado < estados-1)
		{
			for(int i = indice - radio; (contar < umbral) && (i<= indice+radio); i++)
			{
				if(celulas[getRelativeIndex(i)]==(cell+1))
				{
					contar++;
				}
			}
			if(contar>=umbral)
			{
				cellnext = cell;
			}
		}
		else if(estado == estados-1)
		{
			for(int i = indice - radio; (contar < umbral) && (i<= indice+radio); i++)
			{

				if(celulas[getRelativeIndex(i)]==0)
				{
					contar++;
				}

			}
			if(contar>= umbral)
			{
				cellnext=0;
			}
		}
		celulasnext[indice] = cellnext;

	}

	@Override
	public String getMaxRule() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final void setRule(String regla)
	{
		umbral = new Short(regla);
	}
}
