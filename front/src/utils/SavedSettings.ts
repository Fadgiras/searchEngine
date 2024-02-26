// import {ContractLevel} from "../pages/accueil/Accueil";
// import {heistContractTypes} from "../generated/GeneratedHeist";

export const leagueName = "Affliction";

export interface SavedSettings {
  name: string
  version: number
  accueil: AccueilSettings
}

export interface AccueilSettings {
  includeHarvest: boolean
  minChaosValue: string
  maxChaosValue: string
  menagerieLimit: boolean
  redAccueilsOnly: boolean
}

export const defaultSettings: SavedSettings = {
  name: "default",
  version: 1,
  accueil: {
    includeHarvest: true,
    minChaosValue: '',
    maxChaosValue: '',
    menagerieLimit: true,
    redAccueilsOnly: true,
  }
}