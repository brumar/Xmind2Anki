package Xmind2Anki;

public class Rank {
private String id;
private int rank_nb;
private int rankmax_nb;
public int getRankmax_nb() {
	return rankmax_nb;
}
Rank(String id,int rank_nb, int rkmax){
	setId(id);
	setRank_nb(rank_nb);
	setRankmax_nb(rkmax);
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public int getRank_nb() {
	return rank_nb;
}
public void setRank_nb(int rank_nb) {
	this.rank_nb = rank_nb;
}
public void setRankmax_nb(int rank_nb) {
	this.rankmax_nb = rank_nb;
}
}
