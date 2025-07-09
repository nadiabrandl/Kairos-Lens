package com.kairosLens.dto;

public class BiasAnalysisResult {
    private String biasSummary;
    private String tone;
    private String reflectionPrompt;
    private PoliticalAlignment politicalAlignment;
    private String toneIndicators;

    public PoliticalAlignment getPoliticalAlignment() {
		return politicalAlignment;
	}

	public void setPoliticalAlignment(PoliticalAlignment politicalAlignment) {
		this.politicalAlignment = politicalAlignment;
	}

	public String getToneIndicators() {
		return toneIndicators;
	}

	public void setToneIndicators(String toneIndicators) {
		this.toneIndicators = toneIndicators;
	}

	public String getBiasSummary() {
        return biasSummary;
    }

    public void setBiasSummary(String biasSummary) {
        this.biasSummary = biasSummary;
    }

    public String getTone() {
        return tone;
    }

    public void setTone(String tone) {
        this.tone = tone;
    }

    public String getReflectionPrompt() {
        return reflectionPrompt;
    }

    public void setReflectionPrompt(String reflectionPrompt) {
        this.reflectionPrompt = reflectionPrompt;
    }
}

