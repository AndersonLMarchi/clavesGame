package br.com.game.clavesgame;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "Points")
public class Points{

	//db columns / var
	@Id @GeneratedValue
	private int id;

	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "claves", nullable = false)
	private int claves;

	@Column(name = "notas", nullable = false)
	private int notas;

	@Column(name = "pontos", nullable = false)
	private int pontos;

	@Column(name = "tempo", nullable = false)
	private int tempo;

	public Points(){}
	
	public Points(String nome, int claves, int notas, int pontos, int tempo){
		setNome(nome);
		setClaves(claves);
		setNotas(notas);
		setPontos(pontos);
		setTempo(tempo);
	}

	//getters
	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public int getClaves() {
		return claves;
	}

	public int getNotas() {
		return notas;
	}

	public int getPontos() {
		return pontos;
	}

	public int getTempo() {
		return tempo;
	}

	//setters
	public void setId(int id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setClaves(int claves) {
		this.claves = claves;
	}

	public void setNotas(int notas) {
		this.notas = notas;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}

	public void setTempo(int tempo) {
		this.tempo = tempo;
	}	
	
}