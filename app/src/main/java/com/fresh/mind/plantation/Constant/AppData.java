package com.fresh.mind.plantation.Constant;

import android.support.v4.app.FragmentActivity;

import com.fresh.mind.plantation.sqlite.LanguageChange;

import java.lang.ref.SoftReference;

/**
 * Created by AND I5 on 04-02-2017.
 */

public class AppData {
    String[] Soil_Type = {"Clay", "Clay Loam", "Alluvial", "Red Sandy Loam", "Coastal Alluvium", "Red Loamy", "Black", "Red", "Alluvium", "Deep Red", "Lateritic"};
    public static String[] TREE_TYPE = {"Timber Trees", "Pulpwood Trees", "Matchwood Trees", "Firewood, Fuelwood and Fodder Trees", "Other Beneficial Trees (NTFP)", "Biofuel Species",
            "Pencilwood / Plywood Trees", "Industrial / Institutional Plantation Trees"};
    public static String[] ALL_TREES_NAME = {"Acacia Auriculiformis", "Acacia holosericea", "Acrocarpus fraxinifolius", "Ailanthus excelsa", "Ailanthus triphysa/A.Malabarica", "Albizia falcataria", "Albizia lebbeck", "Alstonia scholaris",
            "Anthocephalus cadamba", "Artocarpus heterophyllus", "Azadirichta indica", "Bambusa bambos", "Bambusa vulgaris", "Basia latifolia", "Butea monosperma", "Cassia fistula", "Cassia siamea", "Casuarina equisetifolia", "Casuarina junghuhniana",
            "Cocos nucifera", "Dalbergia latifolia", "Dalbergia sissoo", "Emblica officinalis", "Erythrina indica", "Eucalyptus tereticornis", "Eucalyptus globulus", "Gliricidia sepium", "Gmelina arborea", "Grevilea robusta", "Hardwickia binnata",
            "Jatropha curcas", "Leucaena leucocephala", "Melia dubia", "Murraya koenigii", "Pletophorum pterocarpum", "Pongamia pinnata", "Pterocarpus santalinus", "Sesbania grandiflora", "Syzgium cumini", "Tamarindus indica", "Tectona grandis",
            "Thespesia populnea", "Toona ciliata"};
    public static String[] ALL_TREES_SUB_NAME = {"Australian Wattle/Kathi savuku", "Silver Leaf Wattle/Mankathu", "Pink Cedar/Malaikonnai", "Tree of Heaven/Perumaram", "Mattipal", "Batai", "Indian siris/Vagai",
            "Devil’s Tree/Ezilai Paalai", "Kadam", "Jackfruit tree/ Palaa", "Neem/Vembu", "Kal Moongil", "Thornless Bamboo", "Illupai", "Flame of the Forest", "Golden Shower/Amaltas/Konrai",
            "Manjal konrai", "Savukku", "Savukku", "Coconut Tree", "Indian Rosewood", "Shisham", "Amla/Nelli", "Coral Tree/Kaliyana Murungai", "Nilgiri tree", "Nilgiri tree", "Seemai Agathi", "White Teak/Kumil",
            "Silver Oak", "Acha tree", "Kattamanakku", "Subabul", "Malai Vembu", "Kariveppilai", "Copper Pod Tree", "Pongam Tree", "Red Sanders", "Agathi", "Jamun", "Imli/Puli", "Teak", "Portia/Puvarasu",
            "Red Cedar/Santhana Vembu"};
    public static String[] DISTRICTS_NAME = {"Ariyalur", "Chennai", "Coimbatore", "Cuddalore", "Dharmapuri", "Dindigul (except for Kodaikanal)", "Erode", "Kancheepuram", "Kanniyakumari", "Karur",
            "Kodaikanal (Dindugal)", "Krishnagiri", "Madurai", "Nagapattinam", "Namakkal", "Perambalur", "Pudukkottai", "Ramanathapuram", "Salem", "Sivagangai", "Thanjavur", "The Nilgiris", "Theni",
            "Thiruvallur", "Thiruvarur", "Thoothukkudi", "Tiruchirappalli", "Tirunelveli", "Tiruppur", "Tiruvannamalai", "Vellore", "Viluppuram", "Virudhunagar"};

    //ariyalur
    public static String[] ARIYALUR = {"Tectona grandis", "Gmelina arborea", "Dalbergia sissoo", "Thespesia populnea", "Hardwickia binnata", "Eucalyptus tereticornis", "Casuarina equisetifolia", "Bambusa bambos", "Ailanthus excelsa", "Melia dubia", "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia Auriculiformis", "Acacia holosericea", "Pongamia pinnata", "Cocos nucifera", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica", "Artocarpus heterophyllus", "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica", "Anthocephalus cadamba", "Alstonia scholaris", "Melia dubia", "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Gmelina arborea", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Thespesia populnea", "Tamarindus indica", "Tectona grandis"};
    public static String[] ARIYALUR_ALLUVIAL_SOIL = {"Tectona grandis", "Gmelina arborea", "Dalbergia sissoo", "Thespesia populnea", "Hardwickia binnata",
            "Eucalyptus tereticornis", "Casuarina equisetifolia", "Bambusa bambos",
            "Ailanthus excelsa", "Ailanthus triphysa/A.Malabarica", "Melia dubia",
            "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia Auriculiformis", "Acacia holosericea", "Pongamia pinnata", "Cocos nucifera",
            "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica", "Artocarpus heterophyllus", "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Anthocephalus cadamba", "Alstonia scholaris", "Melia dubia",
            "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Gmelina arborea", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Thespesia populnea", "Tamarindus indica", "Tectona grandis"};
    public static String[] ARIYALUR_ALLIUVIAL_CAT = {"Timber Trees", "Timber Trees", "Timber Trees", "Timber Trees", "Timber Trees",
            "Pulpwood Trees", "Pulpwood Trees", "Pulpwood Trees",
            "Matchwood Trees", "Matchwood Trees", "Matchwood Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Biofuel Species", "Biofuel Species", "Biofuel Species",
            "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"};
    public static String[] ARIYALUR_BLACK_SOIL = {"Tectona grandis", "Gmelina arborea", "Hardwickia binnata",
            "Eucalyptus tereticornis", "Casuarina equisetifolia",
            "Ailanthus excelsa", "Ailanthus triphysa/A.Malabarica", "Melia dubia",
            "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia holosericea", "Pongamia pinnata", "Cocos nucifera", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica", "Artocarpus heterophyllus", "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica", "Alstonia scholaris", "Melia dubia", "Albizia lebbeck", "Azadirichta indica", "Cassia fistula", "Gmelina arborea", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Tamarindus indica", "Tectona grandis"};
    public static String[] ARIYALUR_BLACK_SOIL_CAT = {"Timber Trees", "Timber Trees", "Timber Trees",
            "Pulpwood Trees", "Pulpwood Trees",
            "Matchwood Trees", "Matchwood Trees", "Matchwood Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Biofuel Species", "Biofuel Species", "Biofuel Species",
            "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"


    };
    public static String[] ARIYALUR_SOIL_TYPE = {"Alluvial Soil", "Black Soil"};

    public static String[] ARIYALUR_RAIN_FALLS = {"Moderate"};
    public static String[] ARIYALUR_TERRAIN_TYPE = {"Plains"};

    public static String[] CHENNAI_SOIL_TYPE = {"Alluvial Soil", "Clayey Soil"};
    public static String[] CHENNAI_RAIN_FALL = {"High"};
    public static String[] CHENNAI_TERRAIN_TYPE = {"Plains / Coastal Plains"};
    public static String[] CHENNAI_CLAYEY = {"Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia Auriculiformis", "Acacia holosericea",
            "Pongamia pinnata", "Cocos nucifera", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica", "Artocarpus heterophyllus", "Jatropha curcas", "Pongamia pinnata",
            "Azadirichta indica", "Alstonia scholaris", "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini",
            "Thespesia populnea", "Tamarindus indica"};
    public static String[] CHENNAI_CLAYEY_CAT = {"Firewood, Fuelwod and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Other Beneficial Trees (NTFP)",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Biofuel Species",
            "Biofuel Species", "Biofuel Species", "Pencilwood / Plywood Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"};
    public static String[] CHENNAI_ALLUVIAL = {"Tectona grandis", "Gmelina arborea", "Dalbergia sissoo", "Thespesia populnea", "Eucalyptus tereticornis", "Casuarina equisetifolia", "Bambusa vulgaris",
            "Melia dubia", "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia Auriculiformis", "Acacia holosericea", "Pongamia pinnata",
            "Cocos nucifera", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica", "Artocarpus heterophyllus", "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Alstonia scholaris", "Melia dubia", "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Gmelina arborea", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini",
            "Thespesia populnea", "Tamarindus indica", "Tectona grandis"};
    public static String[] CHENNAI_ALLUVIAL_CAT = {"Timber Trees", "Timber Trees", "Timber Trees", "Timber Trees", "Pulpwood Trees", "Pulpwood Trees", "Pulpwood Trees", "Matchwood Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Biofuel Species", "Biofuel Species", "Biofuel Species", "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"};

    public static String[] CHENNAI = {"Tectona grandis", "Gmelina arborea", "Dalbergia sissoo", "Thespesia populnea", "Toona ciliata", "Acrocarpus fraxinifolius", "E. globulus", "Casuarina equisetifolia",
            "Casuarina junghuhniana", "Bambusa bamboo", "Melia dubia", " Cassia siamea", "Erythrina indica", "Leucaena leucocephala (Subabul)", "Sesbania grandiflora(Agathi)", "Gliricidia sepium",
            "Acacia holosericea", "Pongamia pinnata", "Cocos nucifera", "Murraya koenigii", "Azadirichta indica", "Tamarindus indica", "Artocarpus heterophyllus", "Albizia Falcataria ", "Alstonia scholaris",
            " Albizia lebbeck", " Azadirichta indica", " Butea monosperma", "Cassia fistula", "Gmelina arborea", "Pongamia pinnata", "Syzgium cumini", "Thespesia populnea", "Tamarindus indica", "Tectona grandis"};

    public static String[] COIMBATORE_SOIL_TYPE = {"Black Soil", "Red Loadmy Soil"};
    public static String[] COIMBATORE_RAIN_FALL = {"Low"};
    public static String[] COIMBATORE_TERRAIN_TYPE = {"Plains"};
    public static String[] COIMBATORE_RED_LOAMY_SOIL = {"Tectona grandis", "Gmelina arborea", "Thespesia populnea", "Hardwickia binnata", "Eucalyptus tereticornis", "Casuarina junghuhniana", "Bambusa vulgaris",
            "Ailanthus excelsa", "Ailanthus triphysa/A.Malabarica", "Melia dubia", "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia holosericea",
            "Pongamia pinnata", "Cocos nucifera", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica", "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Anthocephalus cadamba", "Alstonia scholaris", "Melia dubia", "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Gmelina arborea", "Pletophorum pterocarpum",
            "Pongamia pinnata", "Syzgium cumini", "Thespesia populnea", "Tamarindus indica", "Tectona grandis"};
    public static String[] COIMBATORE_RED_LOAMY_SOIL_CAT = {"Timber Trees", "Timber Trees", "Timber Trees", "Timber Trees", "Pulpwood Trees", "Pulpwood Trees", "Pulpwood Trees", "Matchwood Trees", "Matchwood Trees", "Matchwood Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Biofuel Species", "Biofuel Species", "Biofuel Species", "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"};
    public static String[] COIMBATORE_BLACK_SOIL = {"Tectona grandis", "Gmelina arborea", "Thespesia populnea", "Eucalyptus tereticornis", "Casuarina junghuhniana", "Bambusa vulgaris", "Ailanthus excelsa",
            "Ailanthus triphysa/A.Malabarica", "Melia dubia", "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia holosericea", "Pongamia pinnata",
            "Cocos nucifera", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica", "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica", "Melia dubia", "Albizia lebbeck",
            "Azadirichta indica", "Cassia fistula", "Gmelina arborea", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Thespesia populnea", "Tamarindus indica", "Tectona grandis"};
    public static String[] COIMBATORE_BLACK_SOIL_CAT = {"Timber Trees", "Timber Trees", "Timber Trees",
            "Pulpwood Trees", "Pulpwood Trees", "Pulpwood Trees",
            "Matchwood Trees", "Matchwood Trees", "Matchwood Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Biofuel Species", "Biofuel Species", "Biofuel Species",
            "Pencilwood / Plywood Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"};
//Eucalyptus tereticornis)


    public static String[] CUDDALORE_SOIL_TYPE = {"Clayey Loam Soil", "Coastal Alluvial Soil", "Red Sandy Loam Soil"};
    public static String[] CUDDALORE_RAIN_FALL = {"High"};
    public static String[] CUDDALORE_TERRAIN_TYPE = {"Plains / Coastal Plains"};
    public static String[] CUDDALORE_RED_SANDY_LOAM = {"Tectona grandis", "Gmelina arborea", "Thespesia populnea", "Hardwickia binnata",
            "Eucalyptus tereticornis", "Casuarina equisetifolia", "Bambusa vulgaris",
            "Ailanthus excelsa", "Melia dubia",
            "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia Auriculiformis ", "Acacia holosericea", "Pongamia pinnata",
            "Cocos nucifera", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Artocarpus heterophyllus",
            "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Alstonia scholarisn", "Melia dubia",
            "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Gmelina arborea", "Pongamia pinnata", "Syzgium cumini", "Thespesia populnea", "Tectona grandis"};
    public static String[] CUDDALORE_RED_SANDY_LOAM_CAT = {"Timber Trees", "Timber Trees", "Timber Trees", "Timber Trees",
            "Pulpwood Trees", "Pulpwood Trees", "Pulpwood Trees",
            "Matchwood Trees", "Matchwood Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Biofuel Species", "Biofuel Species", "Biofuel Species", "" +
            "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees"};
    public static String[] Cuddalore_Clayey_Loam_Soil = {"Tectona grandis", "Thespesia populnea", "Eucalyptus tereticornis", "Casuarina equisetifolia", "Cassia siamea", "Erythrina indica", "Leucaena leucocephala",
            "Sesbania grandiflora", "Gliricidia sepium", "Acacia Auriculiformis ", "Acacia holosericea", "Cocos nucifera", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Jatropha curcas",
            "Azadirichta indica", "Alstonia scholaris", "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Gmelina arborea", "Syzgium cumini", "Thespesia populnea", "Tectona grandis",};
    public static String[] Cuddalore_Clayey_Loam_Soil_cat = {"Timber Trees", "Timber Trees", "Pulpwood Trees", "Pulpwood Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Biofuel Species", "Biofuel Species", "Pencilwood / Plywood Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"};
    public static String[] Cuddalore_Coastal_Alluvial_Soil = {"Tectona grandis", "Gmelina arborea", "Dalbergia sissoo", "Thespesia populnea", "Hardwickia binnata", "Eucalyptus tereticornis",
            "Casuarina equisetifolia", "Bambusa vulgaris", "Melia dubia", "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia Auriculiformis ",
            "Acacia holosericea", "Cocos nucifera", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica", "Artocarpus heterophyllus", "Jatropha curcas", "Azadirichta indica",
            "Alstonia scholaris", "Melia dubia", "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Gmelina arborea", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini",
            "Thespesia populnea", "Tamarindus indica", "Tectona grandis",};
    public static String[] Cuddalore_Coastal_Alluvial_Soil_cat = {"Timber Trees", "Timber Trees", "Timber Trees", "Timber Trees", "Timber Trees", "Pulpwood Trees", "Pulpwood Trees", "Pulpwood Trees",
            "Matchwood Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Biofuel Species", "Biofuel Species", "Pencilwood / Plywood Trees",
            "Pencilwood / Plywood Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"};


    public static String[] DHARMAPURI_SOIL_TYPE = {"Black Soil", "Red Soil"};
    public static String[] DHARMAPURI_RAIN_FALL = {"Low"};
    public static String[] DHARMAPURI_TERRAIN_TYPE = {"Plains"};
    public static String[] Dharmapuri_Red_Soil = {"Tectona grandis", "Dalbergia latifolia", "Hardwickia binnata", "Pterocarpus santalinus", "Eucalyptus tereticornis", "Casuarina junghuhniana", "Bambusa vulgaris",
            "Ailanthus excelsa", "Ailanthus triphysa/A.Malabarica", "Melia dubia", "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Gliricidia sepium", "Acacia holosericea", "Pongamia pinnata",
            "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica", "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica", "Alstonia scholaris", "Melia dubia", "Albizia lebbeck",
            "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Dalbergia latifolia", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Tamarindus indica", "Tectona grandis"};
    public static String[] Dharmapuri_Red_Soil_cat = {"Timber Trees", "Timber Trees", "Timber Trees", "Timber Trees", "Pulpwood Trees", "Pulpwood Trees", "Pulpwood Trees", "Matchwood Trees", "Matchwood Trees",
            "Matchwood Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Other Beneficial Trees (NTFP)", "Biofuel Species", "Biofuel Species", "Biofuel Species", "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees"};
    public static String[] Dharmapuri_Black_Soil = {"Tectona grandis", "Casuarina junghuhniana", "Bambusa vulgaris", "Ailanthus excelsa", "Ailanthus triphysa/A.Malabarica", "Melia dubia", "Cassia siamea",
            "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia holosericea", "Pongamia pinnata", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica",
            "Tamarindus indica", "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica", "Alstonia scholaris", "Melia dubia", "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula",
            "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Tamarindus indica", "Tectona grandis"};
    public static String[] Dharmapuri_Black_Soil_cat = {"Timber Trees", "Pulpwood Trees", "Pulpwood Trees", "Matchwood Trees", "Matchwood Trees", "Matchwood Trees", "Firewood, Fuelwood and Fodder Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Firewood, Fuelwood and Fodder Trees", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Biofuel Species",
            "Biofuel Species", "Biofuel Species", "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"};


    public static String[] DINDUGAL_SOIL_TYPE = {"Black Soil", "Red Loamy Soil"};
    public static String[] DINDUGAL_RAIN_FALL = {"Low"};
    public static String[] DINDUGAL_TERRAIN_TYPE = {"Plains"};
    public static String[] Dindigul__Black_Soil = {"Tectona grandis", "Gmelina arborea", "Thespesia populnea", "Eucalyptus tereticornis", "Casuarina junghuhniana", "Bambusa vulgaris", "Ailanthus excelsa",
            "Ailanthus triphysa/A.Malabarica", "Melia dubia", "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia holosericea", "Pongamia pinnata",
            "Cocos nucifera", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica", "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica", "Alstonia scholaris", "Melia dubia",
            "Albizia lebbeck", "Azadirichta indica", "Cassia fistula", "Gmelina arborea", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Thespesia populnea", "Tamarindus indica", "Tectona grandis"};
    public static String[] Dindigul__Black_Soil_cat = {"Timber Trees", "Timber Trees", "Timber Trees", "Pulpwood Trees", "Pulpwood Trees", "Pulpwood Trees", "Matchwood Trees", "Matchwood Trees", "Matchwood Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Biofuel Species", "Biofuel Species", "Biofuel Species", "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"};
    public static String[] Dindigul__Red_Loamy_Soil = {"Tectona grandis", "Gmelina arborea", "Thespesia populnea", "Hardwickia binnata", "Eucalyptus tereticornis", "Casuarina junghuhniana",
            "Bambusa vulgaris", "Ailanthus excelsa", "Ailanthus triphysa/A.Malabarica", "Melia dubia", "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium",
            "Acacia holosericea", "Pongamia pinnata", "Cocos nucifera", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica", "Jatropha curcas", "Pongamia pinnata",
            "Azadirichta indica", "Anthocephalus cadamba", "Alstonia scholaris", "Melia dubia", "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Gmelina arborea", "Pletophorum pterocarpum",
            "Pongamia pinnata", "Syzgium cumini", "Thespesia populnea", "Tamarindus indica", "Tectona grandis"};
    public static String[] Dindigul__Red_Loamy_Soil_cat = {"Timber Trees", "Timber Trees", "Timber Trees", "Timber Trees", "Pulpwood Trees", "Pulpwood Trees", "Pulpwood Trees", "Matchwood Trees",
            "Matchwood Trees", "Matchwood Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Biofuel Species", "Biofuel Species", "Biofuel Species", "Pencilwood / Plywood Trees",
            "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"};


    public static String[] KODAIKAANAL_DINDUGAL_SOIL_TYPE = {"Lateritic Soil"};
    public static String[] KODAIKAANAL_DINDUGAL_RAIN_FALL = {"High"};
    public static String[] KODAIKAANAL_DINDUGAL_TERRAIN_TYPE = {"Hills"};
    public static String[] Kodaikanal_Lateritic_Soil = {"Toona ciliata", "Acrocarpus fraxinifolius", "Eucalyptus globulus", "Ailanthus triphysa/A.Malabarica", "Acacia Auriculiformis", "Artocarpus heterophyllus",
            "Albizia falcataria ", "Grevilea robusta", "Grevilea robusta"};
    public static String[] Kodaikanal_Lateritic_Soil_cat = {"Timber Trees", "Timber Trees", "Pulpwood Trees", "Matchwood Trees", "Firewood, Fuelwood and Fodder Trees", "Other Beneficial Trees (NTFP)",
            "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees", "Industrial / Institutional Plantation Trees"};


    public static String[] ERODE_SOIL_TYPE = {"Black Soil", "Red Loamy Soil"};
    public static String[] ERODE_RAIN_FALL = {"Low"};
    public static String[] ERODE_TERRAIN_TYPE = {"Plains"};
    public static String[] Erode_Red_Loamy_Soil = {"Tectona grandis", "Gmelina arborea", "Thespesia populnea", "Hardwickia binnata", "Eucalyptus tereticornis", "Casuarina junghuhniana", "Bambusa vulgaris",

            "Ailanthus excelsa", "Ailanthus triphysa/A.Malabarica", "Melia dubia", "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium",
            "Acacia holosericea", "Pongamia pinnata", "Cocos nucifera", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica", "Jatropha curcas",
            "Pongamia pinnata", "Azadirichta indica", "Anthocephalus cadamba", "Alstonia scholaris", "Melia dubia", "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Gmelina arborea",
            "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Thespesia populnea", "Tamarindus indica", "Tectona grandis"};
    public static String[] Erode_Red_Loamy_Soil_cat = {"Timber Trees", "Timber Trees", "Timber Trees", "Timber Trees", "Pulpwood Trees", "Pulpwood Trees", "Pulpwood Trees", "Matchwood Trees", "Matchwood Trees",
            "Matchwood Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Biofuel Species", "Biofuel Species", "Biofuel Species", "Pencilwood / Plywood Trees",
            "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"};
    public static String[] Erode_Black_Soil = {"Tectona grandis", "Gmelina arborea", "Thespesia populnea", "Eucalyptus tereticornis", "Casuarina junghuhniana", "Bambusa vulgaris", "Ailanthus excelsa",
            "Ailanthus triphysa/A.Malabarica", "Melia dubia", "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia holosericea", "Pongamia pinnata",
            "Cocos nucifera", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica", "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica", "Alstonia scholaris", "Melia dubia",
            "Albizia lebbeck", "Azadirichta indica", "Cassia fistula", "Gmelina arborea", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Thespesia populnea", "Tamarindus indica", "Tectona grandis"};
    public static String[] Erode_Black_Soil_cat = {"Timber Trees", "Timber Trees", "Timber Trees", "Pulpwood Trees", "Pulpwood Trees", "Pulpwood Trees", "Matchwood Trees", "Matchwood Trees", "Matchwood Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Biofuel Species", "Biofuel Species", "Biofuel Species", "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"};


    public static String[] KANCHEEPURAM_SOIL_TYPE = {"Clayey Soil", "Coastal Alluvial Soil", "Red Soil"};
    public static String[] KANCHEEPURAM_RAIN_FALL = {"High"};
    public static String[] KANCHEEPURAM_TERRAIN_TYPE = {"Plains / Coastal Pains"};
    public static String[] Kancheepuram_Clayey_Soil = {"Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia Auriculiformis ", "Acacia holosericea",
            "Pongamia pinnata", "Cocos nucifera", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica", "Artocarpus heterophyllus", "Jatropha curcas", "Pongamia pinnata",
            "Azadirichta indica", "Alstonia scholaris", "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini",
            "Thespesia populnea", "Tamarindus indica "};
    public static String[] Kancheepuram_Clayey_Soil_cat = {"Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Other Beneficial Trees (NTFP)",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Biofuel Species", "Biofuel Species",
            "Biofuel Species", "Pencilwood / Plywood Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"};
    public static String[] Kancheepuram_Red_Soil = {"Tectona grandis", "Dalbergia latifolia", "Gmelina arborea", "Hardwickia binnata", "Eucalyptus tereticornis", "Casuarina equisetifolia", "Bambusa vulgaris",
            "Ailanthus excelsa", "Melia dubia", "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Gliricidia sepium", "Acacia Auriculiformis", "Acacia holosericea", "Pongamia pinnata", "Cocos nucifera",
            "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica", "Artocarpus heterophyllus", "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica", "Alstonia scholaris",
            "Melia dubia", "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Dalbergia latifolia", "Gmelina arborea", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini",
            "Tamarindus indica", "Tectona grandis"};
    public static String[] Kancheepuram_Red_Soil_cat = {"Timber Trees", "Timber Trees", "Timber Trees", "Timber Trees", "Pulpwood Trees", "Pulpwood Trees", "Pulpwood Trees", "Matchwood Trees", "Matchwood Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Biofuel Species", "Biofuel Species", "Biofuel Species", "Pencilwood / Plywood Trees",
            "Pencilwood / Plywood Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"};
    public static String[] Kancheepuram_Coastal_Alluvial_Soil = {"Tectona grandis", "Gmelina arborea", "Dalbergia sissoo", "Thespesia populnea", "Eucalyptus tereticornis", "Casuarina equisetifolia",
            "Bambusa vulgaris", "Melia dubia", "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia Auriculiformis ", "Acacia holosericea",
            "Pongamia pinnata", "Cocos nucifera", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica", "Artocarpus heterophyllus", "Jatropha curcas", "Pongamia pinnata",
            "Azadirichta indica", "Alstonia scholaris", "Melia dubia", "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Gmelina arborea", "Pletophorum pterocarpum",
            "Pongamia pinnata", "Syzgium cumini", "Thespesia populnea", "Tamarindus indica", "Tectona grandis"};
    public static String[] Kancheepuram_Coastal_Alluvial_Soil_cat = {"Timber Trees", "Timber Trees", "Timber Trees", "Timber Trees", "Pulpwood Trees", "Pulpwood Trees", "Pulpwood Trees", "Matchwood Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Biofuel Species", "Biofuel Species", "Biofuel Species",
            "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"};


    public static String[] KANNIYAKUMARI_SOIL_TYPE = {"Coastal Alluvial Soil", "Deep Red Soil"};
    public static String[] KANNIYAKUMARI_RAIN_FALL = {"High"};
    public static String[] KANNIYAKUMARI_TERRAIN_TYPE = {"Plains / Coastal Pains"};
    public static String[] Kanniyakumari_Deep_Red_Soil = {"Acrocarpus fraxinifolius", "Bambusa bambos", "Melia dubia", "Acacia Auriculiformis ", "Artocarpus heterophyllus", "Anthocephalus cadamba",
            "Albizia falcataria ", "Grevilea robusta", "Melia dubia", "Grevilea robusta"};
    public static String[] Kanniyakumari_Deep_Red_Soil_cat = {"Timber Trees", "Pulpwood Trees", "Matchwood Trees", "Firewood, Fuelwood and Fodder Trees", "Other Beneficial Trees (NTFP)",
            "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees", "Industrial / Institutional Plantation Trees"};
    public static String[] Kanniyakumari_Coastal_Alluvial_Soil = {"Acrocarpus fraxinifolius",
            "Bambusa bambos",
            "Melia dubia",
            "Acacia Auriculiformis",
            "Artocarpus heterophyllus",
            "Anthocephalus cadamba", "Albizia falcataria ", "Melia dubia"};
    public static String[] Kanniyakumari_Coastal_Alluvial_Soil_cat = {"Timber Trees",
            "Pulpwood Trees",
            "Matchwood Trees",
            "Firewood, Fuelwood and Fodder Trees",
            "Other Beneficial Trees (NTFP)",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",

    };


    public static String[] KARUR_SOIL_TYPE = {"Red Loamy Soil", "Red Soil"};
    public static String[] KARUR_RAIN_FALL = {"Low"};
    public static String[] KARUR_TERRAIN_TYPE = {"Plains"};
    public static String[] Karur_Red_Soil = {"Dalbergia latifolia", "Gmelina arborea", "Hardwickia binnata",
            "Eucalyptus tereticornis", "Casuarina junghuhniana", "Bambusa vulgaris",
            "Ailanthus excelsa", "Ailanthus triphysa/A.Malabarica", "Melia dubia",
            "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Gliricidia sepium", "Acacia holosericea", "Pongamia pinnata",
            "Cocos nucifera", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica",
            "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Anthocephalus cadamba", "Alstonia scholaris", "Melia dubia",
            "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Dalbergia latifolia", "Gmelina arborea", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Tamarindus indica",
            "Tectona grandis"};
    public static String[] Karur_Red_Soil_cat = {"Timber Trees", "Timber Trees", "Timber Trees",
            "Pulpwood Trees", "Pulpwood Trees", "Pulpwood Trees",
            "Matchwood Trees", "Matchwood Trees", "Matchwood Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Biofuel Species", "Biofuel Species", "Biofuel Species",
            "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"};
    public static String[] Karur_Red_Loamy_Soil = {"Tectona grandis", "Gmelina arborea", "Thespesia populnea", "Hardwickia binnata", "Eucalyptus tereticornis", "Casuarina junghuhniana", "Bambusa vulgaris", "Ailanthus excelsa", "Ailanthus triphysa/A.Malabarica", "Melia dubia", "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia holosericea", "Pongamia pinnata", "Cocos nucifera", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica", "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica", "Anthocephalus cadamba", "Alstonia scholaris", "Melia dubia", "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Gmelina arborea", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Thespesia populnea", "Tamarindus indica", "Tectona grandis"};
    public static String[] Karur_Red_Loamy_Soil_cat = {"Timber Trees", "Timber Trees", "Timber Trees", "Timber Trees", "Pulpwood Trees", "Pulpwood Trees", "Pulpwood Trees", "Matchwood Trees", "Matchwood Trees", "Matchwood Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Biofuel Species", "Biofuel Species", "Biofuel Species", "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"};

    public static String[] KRISHNAGIRI_SOIL_TYPE = {"Red Soil", "Black Soil"};

    public static String[] Krishnagiri_Red_Soil = {"Tectona grandis", "Dalbergia latifolia", "Hardwickia binnata", "Pterocarpus santalinus", "Eucalyptus tereticornis", "Casuarina junghuhniana", "Bambusa vulgaris", "Ailanthus excelsa", "Ailanthus triphysa/A.Malabarica", "Melia dubia", "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Gliricidia sepium", "Acacia holosericea", "Pongamia pinnata", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica", "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica", "Alstonia scholaris", "Melia dubia", "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Dalbergia latifolia", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Tamarindus indica", "Tectona grandis"};
    public static String[] Krishnagiri_Red_Soil_cat = {"Timber Trees", "Timber Trees", "Timber Trees", "Timber Trees", "Pulpwood Trees", "Pulpwood Trees", "Pulpwood Trees", "Matchwood Trees", "Matchwood Trees", "Matchwood Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Biofuel Species", "Biofuel Species", "Biofuel Species", "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"};
    public static String[] Krishnagiri_Black_Soil = {"Tectona grandis", "Casuarina junghuhniana", "Bambusa vulgaris", "Ailanthus excelsa", "Ailanthus triphysa/A.Malabarica", "Melia dubia", "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia holosericea", "Pongamia pinnata", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica", "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica", "Alstonia scholaris", "Melia dubia", "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Tamarindus indica", "Tectona grandis"};
    public static String[] Krishnagiri_Black_Soil_cat = {"Timber Trees", "Pulpwood Trees", "Pulpwood Trees", "Matchwood Trees", "Matchwood Trees", "Matchwood Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Biofuel Species", "Biofuel Species", "Biofuel Species", "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"};

    public static String[] MADURAI_SOIL_TYPE = {"Red Sandy Loam  Soil", "Alluvial Soil"};
    public static String[] Madurai_Red_Sandy_Loam_Soil = {"Tectona grandis", "Thespesia populnea", "Hardwickia binnata", "Eucalyptus tereticornis", "Casuarina junghuhniana", "Bambusa vulgaris", "Ailanthus excelsa", "Ailanthus triphysa/A.Malabarica", "Melia dubia", "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia holosericea", "Pongamia pinnata", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica", "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica", "Alstonia scholaris", "Melia dubia", "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Thespesia populnea", "Tamarindus indica", "Tectona grandis"};
    public static String[] Madurai_Red_Sandy_Loam_Soil_cat = {"Timber Trees", "Timber Trees", "Timber Trees", "Pulpwood Trees", "Pulpwood Trees", "Pulpwood Trees", "Matchwood Trees", "Matchwood Trees", "Matchwood Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Biofuel Species", "Biofuel Species", "Biofuel Species", "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"};
    public static String[] Madurai_Alluvial_Soil = {"Tectona grandis", "Dalbergia sissoo", "Thespesia populnea", "Casuarina junghuhniana", "Bambusa vulgaris", "Melia dubia", "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia holosericea", "Pongamia pinnata", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica", "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica", "Alstonia scholaris", "Melia dubia", "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Thespesia populnea", "Tamarindus indica", "Tectona grandis"};
    public static String[] Madurai_Alluvial_Soil_cat = {"Timber Trees", "Timber Trees", "Timber Trees", "Pulpwood Trees", "Pulpwood Trees", "Matchwood Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Biofuel Species", "Biofuel Species", "Biofuel Species", "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"};

    public static String[] NAGAPATTINAM_SOILTYPE = {"Black Soil", "Clayey Loam Soil"};
    public static String[] Nagapattinam_Black_Soil = {"Tectona grandis", "Gmelina arborea", "Hardwickia binnata",
            "Eucalyptus tereticornis", "Casuarina equisetifolia",
            "Ailanthus excelsa", "Ailanthus triphysa/A.Malabarica", "Melia dubia",
            "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia holosericea", "Pongamia pinnata",
            "Cocos nucifera", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica", "Artocarpus heterophyllus",
            "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Alstonia scholaris", "Melia dubia",
            "Albizia lebbeck", "Azadirichta indica", "Cassia fistula", "Gmelina arborea", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Tamarindus indica", "Tectona grandis"};
    public static String[] Nagapattinam_Black_Soil_cat = {"Timber Trees", "Timber Trees", "Timber Trees", "Pulpwood Trees", "Pulpwood Trees", "Matchwood Trees", "Matchwood Trees", "Matchwood Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Biofuel Species", "Biofuel Species", "Biofuel Species", "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"};
    public static String[] Nagapattinam_Clayey_Loam_Soil = {"Thespesia populnea", "Eucalyptus tereticornis",
            "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia Auriculiformis", "Acacia holosericea", "Pongamia pinnata",
            "Cocos nucifera", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica", "Artocarpus heterophyllus",
            "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Alstonia scholaris",
            "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Gmelina arborea", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Thespesia populnea", "Tamarindus indica"};
    public static String[] Nagapattinam_Clayey_Loam_Soil_cat = {"Timber Trees", "Pulpwood Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Biofuel Species", "Biofuel Species", "Biofuel Species", "Pencilwood / Plywood Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"};

    public static String[] NAMAKKAL_SOIL_TYPE = {"Red Loamy Soil", "Black Soil", "Alluvial Soil"};
    public static String[] Namakkal_Red_Loamy_Soil = {"Cassia siamea", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia Auriculiformis", "Acacia holosericea", "Pongamia pinnata",
            "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica", "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Alstonia scholaris",
            "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini"};
    public static String[] Namakkal_Red_Loamy_Soil_cat = {"Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Biofuel Species", "Biofuel Species", "Biofuel Species", "Pencilwood / Plywood Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"};
    public static String[] Namakkal_Black_Soil = {"Tectona grandis",
            "Casuarina junghuhniana", "Bambusa vulgaris",
            "Ailanthus excelsa", "Ailanthus triphysa/A.Malabarica", "Melia dubia", "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia holosericea",
            "Pongamia pinnata", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica",
            "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Alstonia scholaris", "Melia dubia", "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula",
            "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Tamarindus indica", "Tectona grandis"};
    public static String[] Namakkal_Black_Soil_cat = {"Timber Trees", "Pulpwood Trees", "Pulpwood Trees", "Matchwood Trees", "Matchwood Trees", "Matchwood Trees", "Firewood, Fuelwood and Fodder Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Firewood, Fuelwood and Fodder Trees", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Biofuel Species",
            "Biofuel Species", "Biofuel Species", "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"};
    public static String[] Namakkal_Alluvial_Soil = {"Tectona grandis", "Dalbergia sissoo", "Pterocarpus santalinus", "Casuarina junghuhniana", "Bambusa vulgaris", "Melia dubia", "Cassia siamea",
            "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia holosericea", "Pongamia pinnata", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica",
            "Tamarindus indica",
            "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Alstonia scholaris", "Melia dubia",
            "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula",
            "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini",
            "Tamarindus indica", "Tectona grandis"};
    public static String[] Namakkal_Alluvial_Soil_cat = {"Timber Trees", "Timber Trees", "Timber Trees", "Pulpwood Trees", "Pulpwood Trees", "Matchwood Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Biofuel Species", "Biofuel Species", "Biofuel Species", "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"};

    public static String[] PERAMPALUR_SOIL_TYPE = {"Black Soil", "Red Loamy Soil", "Alluvial Soil"};
    public static String[] Perambalur_Black_Soil = {"Tectona grandis", "Gmelina arborea", "Hardwickia binnata", "Eucalyptus tereticornis", "Casuarina equisetifolia", "Ailanthus excelsa", "Ailanthus triphysa/A.Malabarica", "Melia dubia", "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia holosericea", "Pongamia pinnata", "Cocos nucifera", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica", "Artocarpus heterophyllus", "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica", "Alstonia scholaris", "Melia dubia", "Albizia lebbeck", "Azadirichta indica", "Cassia fistula", "Gmelina arborea", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Tamarindus indica", "Tectona grandis"};
    public static String[] Perambalur_Black_Soil_cat = {"Timber Trees", "Timber Trees", "Timber Trees", "Pulpwood Trees", "Pulpwood Trees", "Matchwood Trees", "Matchwood Trees", "Matchwood Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Biofuel Species", "Biofuel Species", "Biofuel Species", "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"};
    public static String[] Perambalur_Red_Loamy_Soil = {"Cassia siamea", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia Auriculiformis", "Acacia holosericea", "Pongamia pinnata",
            "Cocos nucifera", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica", "Artocarpus heterophyllus",
            "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Anthocephalus cadamba", "Alstonia scholaris",
            "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini"};
    public static String[] Perambalur_Red_Loamy_Soil_cat = {"Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Biofuel Species", "Biofuel Species", "Biofuel Species", "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"};
    public static String[] Perambalur_Alluvial_Soil = {"Tectona grandis", "Gmelina arborea", "Dalbergia sissoo", "Thespesia populnea", "Hardwickia binnata",
            "Eucalyptus tereticornis", "Casuarina equisetifolia", "Bambusa bambos",
            "Ailanthus excelsa", "Ailanthus triphysa/A.Malabarica", "Melia dubia",
            "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia Auriculiformis ", "Acacia holosericea", "Pongamia pinnata",
            "Cocos nucifera", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica", "Artocarpus heterophyllus",
            "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Anthocephalus cadamba", "Alstonia scholaris", "Melia dubia",
            "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Gmelina arborea",
            "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Thespesia populnea", "Tamarindus indica", "Tectona grandis"};
    public static String[] Perambalur_Alluvial_Soil_cat = {"Timber Trees", "Timber Trees", "Timber Trees", "Timber Trees", "Timber Trees", "Pulpwood Trees", "Pulpwood Trees", "Pulpwood Trees", "Matchwood Trees", "Matchwood Trees", "Matchwood Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Biofuel Species", "Biofuel Species", "Biofuel Species", "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"};

    public static String[] PUDUKOTTAI_SOIL_TYPE = {"Red Soil", "Red Sandy Loam  Soil", "Black Soil"};
    public static String[] Pudukkottai_Red_Soil = {"Tectona grandis", "Gmelina arborea", "Hardwickia binnata",
            "Eucalyptus tereticornis", "Casuarina equisetifolia", "Bambusa bambos",
            "Ailanthus excelsa", "Ailanthus triphysa/A.Malabarica", "Melia dubia",
            "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "GliricidiaÂ sepium", "Acacia AuriculiformisÂ ", "Acacia holosericea", "Pongamia pinnata",
            "Cocos nucifera", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica", "Artocarpus heterophyllus",
            "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Alstonia scholaris", "Melia dubia",
            "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Gmelina arborea", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Tamarindus indica", "Tectona grandis"};
    public static String[] Pudukkottai_Red_Soil_cat = {"Timber Trees", "Timber Trees", "Timber Trees", "Pulpwood Trees", "Pulpwood Trees", "Pulpwood Trees", "Matchwood Trees", "Matchwood Trees", "Matchwood Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Biofuel Species", "Biofuel Species", "Biofuel Species", "Pencilwood / Plywood Trees",
            "Pencilwood / Plywood Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"};
    public static String[] Pudukkottai_Red_Sandy_Loam_Soil = {"Tectona grandis", "Gmelina arborea", "Thespesia populnea", "Hardwickia binnata",
            "Eucalyptus tereticornis", "Casuarina equisetifolia", "Bambusa bambos",
            "Ailanthus excelsa", "Ailanthus triphysa/A.Malabarica", "Melia dubia",
            "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "GliricidiaÂ sepium", "Acacia AuriculiformisÂ ", "Acacia holosericea", "Pongamia pinnata", "Cocos nucifera",
            "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica", "Artocarpus heterophyllus",
            "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Anthocephalus cadamba", "Alstonia scholaris", "Melia dubia",
            "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Gmelina arborea", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Thespesia populnea",
            "Tamarindus indica", "Tectona grandis"};
    public static String[] Pudukkottai_Red_Sandy_Loam_Soil_cat = {"Timber Trees", "Timber Trees", "Timber Trees", "Timber Trees", "Pulpwood Trees", "Pulpwood Trees", "Pulpwood Trees", "Matchwood Trees",
            "Matchwood Trees", "Matchwood Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Other Beneficial Trees (NTFP)",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Biofuel Species",
            "Biofuel Species", "Biofuel Species", "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"};
    public static String[] Pudukkottai_Black_Soil = {"Tectona grandis", "Gmelina arborea", "Hardwickia binnata",
            "Eucalyptus tereticornis", "Casuarina equisetifolia", "Ailanthus excelsa", "Ailanthus triphysa/A.Malabarica", "Melia dubia",
            "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "GliricidiaÂ sepium", "Acacia holosericea", "Pongamia pinnata",
            "Cocos nucifera", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica", "Artocarpus heterophyllus",
            "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Alstonia scholaris", "Melia dubia",
            "Albizia lebbeck", "Azadirichta indica", "Cassia fistula", "Gmelina arborea", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Tamarindus indica", "Tectona grandis"};
    public static String[] Pudukkottai_Black_Soil_cat = {"Timber Trees", "Timber Trees", "Timber Trees", "Pulpwood Trees", "Pulpwood Trees", "Matchwood Trees", "Matchwood Trees", "Matchwood Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Biofuel Species", "Biofuel Species", "Biofuel Species", "Pencilwood / Plywood Trees",
            "Pencilwood / Plywood Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"};


    //Size 31 31
    public static String[] RAMANATHAPURAM_SOIL_TYPE = {"Black Soil", "Coastal Alluvial Soil"};
    public static String[] RAMANATHAPURAM_BLACK = {"Tectona grandis",
            "Casuarina junghuhniana", "Bambusa vulgaris",
            "Ailanthus excelsa", "Ailanthus triphysa/A.Malabarica", "Melia dubia",
            "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia holosericea", "Pongamia pinnata",
            "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica",
            "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Alstonia scholaris", "Melia dubia",
            "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Tamarindus indica", "Tectona grandis"};

    public static String[] RAMANATHAPURAM_BLACK_CAT = {"Timber Trees",
            "Pulpwood Trees", "Pulpwood Trees",
            "Matchwood Trees", "Matchwood Trees", "Matchwood Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Biofuel Species", "Biofuel Species", "Biofuel Species",
            "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees"

    };
    public static String[] RAMANATHAPURAM_COASTAL = {"Tectona grandis", "Thespesia populnea",
            "Casuarina junghuhniana", "Bambusa vulgaris",
            "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia holosericea", "Pongamia pinnata",
            "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica",
            "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Alstonia scholaris", "Melia dubia",
            "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Thespesia populnea", "Tamarindus indica", "Tectona grandis"};
    public static String[] RAMANATHAPURAM_COASTAL_CAT = {"Timber Trees", "Timber Trees",
            "Pulpwood Trees", "Pulpwood Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Biofuel Species", "Biofuel Species", "Biofuel Species",
            "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"
    };


    public static String[] SELAM_SOIL_TYPE = {"Red Soil", "Black Soil"};

    public static String[] SELAM_RED = {"Tectona grandis", "Dalbergia latifolia", "Hardwickia binnata", "Pterocarpus santalinus",
            "Eucalyptus tereticornis", "Casuarina junghuhniana", "Bambusa vulgaris",
            "Ailanthus excelsa", "Ailanthus triphysa/A.Malabarica", "Melia dubia",
            "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Gliricidia sepium", "Acacia holosericea", "Pongamia pinnata",
            "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica",
            "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Alstonia scholaris", "Melia dubia",
            "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Dalbergia latifolia", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Tamarindus indica", "Tectona grandis"};
    public static String[] SELAM_RED_CAT = {"Timber Trees", "Timber Trees", "Timber Trees", "Timber Trees",
            "Pulpwood Trees", "Pulpwood Trees", "Pulpwood Trees",
            "Matchwood Trees", "Matchwood Trees", "Matchwood Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Biofuel Species", "Biofuel Species", "Biofuel Species",
            "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"
    };
    //Size  31  31
    public static String[] SELAM_BLACK = {"Tectona grandis",
            "Casuarina junghuhniana", "Bambusa vulgaris",
            "Ailanthus excelsa", "Ailanthus triphysa/A.Malabarica", "Melia dubia",
            "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia holosericea", "Pongamia pinnata",
            "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica",
            "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Alstonia scholaris", "Melia dubia",
            "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Tamarindus indica", "Tectona grandis"};
    public static String[] SELAM_BLACK_CAT = {"Timber Trees",
            "Pulpwood Trees", "Pulpwood Trees",
            "Matchwood Trees", "Matchwood Trees", "Matchwood Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Biofuel Species", "Biofuel Species", "Biofuel Species",
            "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees"
    };
    // Size 28 28
    public static String[] SIVAGANGAI_SOIL_TYPE = {"Deep Red Soil", "Black Soil", "Alluvial Soil"};
    public static String[] SIVAGANGAI_DEEP_RED = {"Tectona grandis", "Dalbergia sissoo",
            "Eucalyptus tereticornis", "Casuarina junghuhniana",
            "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia holosericea", "Pongamia pinnata",
            "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica",
            "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Alstonia scholaris",
            "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Dalbergia latifolia", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Tamarindus indica", "Tectona grandis",


    };
    public static String[] SIVAGANGAI_DEEP_RED_CAT = {"Timber Trees", "Timber Trees",
            "Pulpwood Trees", "Pulpwood Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Fuelwood and Fodder Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Biofuel Species", "Biofuel Species", "Biofuel Species",
            "Pencilwood / Plywood Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees"
    };

    //Size 31 31
    public static String[] SIVAGANGAI_BLACK = {"Tectona grandis",
            "Casuarina junghuhniana", "Bambusa vulgaris",
            "Ailanthus excelsa", "Ailanthus triphysa/A.Malabarica", "Melia dubia",
            "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia holosericea", "Pongamia pinnata",
            "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica",
            "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Alstonia scholaris", "Melia dubia",
            "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Tamarindus indica", "Tectona grandis"};
    public static String[] SIVAGANGAI_BLACK_CAT = {"Timber Trees",
            "Pulpwood Trees", "Pulpwood Trees",
            "Matchwood Trees", "Matchwood Trees", "Matchwood Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Biofuel Species", "Biofuel Species", "Biofuel Species",
            "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees"
    };

    //Size  31  31

    public static String[] SIVAGANGAI_ALLUVIAL = {"Tectona grandis", "Dalbergia sissoo", "Thespesia populnea",
            "Casuarina junghuhniana", "Bambusa vulgaris",
            "Melia dubia",
            "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia holosericea", "Pongamia pinnata",
            "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica",
            "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Alstonia scholaris", "Melia dubia",
            "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Tamarindus indica", "Tectona grandis"};

    public static String[] SIVAGANGAI_ALLUVIAL_CAT = {"Timber Trees", "Timber Trees", "Timber Trees",
            "Pulpwood Trees", "Pulpwood Trees",
            "Matchwood Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Biofuel Species", "Biofuel Species", "Biofuel Species",
            "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees"
    };


    //Size 25 25
    public static String[] THANJAVUR_SOIL_TYPe = {"Red Loamy Soil", "Alluvial Soil"};

    public static String[] THANJAVUR_RED_LOAMY = {"Cassia siamea", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia Auriculiformis", "Acacia holosericea", "Pongamia pinnata",
            "Cocos nucifera", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica", "Artocarpus heterophyllus",
            "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Anthocephalus cadamba", "Alstonia scholaris",
            "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini"};
    public static String[] THANJAVUR_RED_LOAMY_CAT = {"Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Biofuel Species", "Biofuel Species", "Biofuel Species",
            "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"
    };

    //Size  38  38
    public static String[] THANJAVUR_ALLUVIAL = {"Tectona grandis", "Gmelina arborea", "Dalbergia sissoo", "Thespesia populnea",
            "Casuarina equisetifolia", "Bambusa bambos", "Melia dubia",
            "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia Auriculiformis", "Acacia holosericea", "Pongamia pinnata",
            "Cocos nucifera", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica", "Artocarpus heterophyllus",
            "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Anthocephalus cadamba", "Alstonia scholaris", "Melia dubia",
            "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Gmelina arborea", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Thespesia populnea", "Tamarindus indica", "Tectona grandis"};
    public static String[] THANJAVUR_ALLUVIAL_CAT = {"Timber Trees", "Timber Trees", "Timber Trees", "Timber Trees",
            "Pulpwood Trees", "Pulpwood Trees",
            "Matchwood Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Biofuel Species", "Biofuel Species", "Biofuel Species",
            "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",

    };
    //Size 9 9
    public static String[] THE_NIGIRIS_SOIL_TYPE = {"Lateritic Soil"};
    public static String[] THE_NILGIRIS_LATERICT = {"Toona ciliata", "Acrocarpus fraxinifolius", "Eucalyptus globulus", "Ailanthus triphysa/A.Malabarica", "Acacia Auriculiformis", "Artocarpus heterophyllus",
            "Albizia falcataria", "Grevilea robusta", "Grevilea robusta",};
    public static String[] THE_NILGIRIS_LATERICT_CAT = {"Timber Trees", "Timber Trees", "Pulpwood Trees", "Matchwood Trees", "Firewood, Fuelwood and Fodder Trees", "Other Beneficial Trees (NTFP)",
            "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees", "Industrial / Institutional Plantation Trees"};
    //Low
    public static String[] THENI_SOIL_TYPE = {"Deep Red Soil", "Black Soil"};
    public static String[] Theni_Terrain_Type = {"Plains", "Hills"};
    public static String[] THENI_DEPP_SOIL = {"Tectona grandis", "Dalbergia latifolia",
            "Eucalyptus tereticornis", "Casuarina junghuhniana",
            "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Gliricidia sepium", "Acacia holosericea", "Pongamia pinnata",
            "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica",
            "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Alstonia scholaris",
            "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Dalbergia latifolia", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Tamarindus indica", "Tectona grandis"};

    public static String[] THENI_DEP_CAT = {"Timber Trees", "Timber Trees",
            "Pulpwood Trees", "Pulpwood Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Firewood, Fuelwood and Fodder Trees",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Biofuel Species", "Biofuel Species", "Biofuel Species",
            "Pencilwood / Plywood Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees"};


    public static String[] THENI_BLACK_SOIL = {"Tectona grandis",
            "Casuarina junghuhniana", "Bambusa vulgaris",
            "Ailanthus excelsa", "Ailanthus triphysa/A.Malabarica", "Melia dubia",
            "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia holosericea", "Pongamia pinnata",
            "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica",
            "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Alstonia scholaris", "Melia dubia",
            "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Tamarindus indica", "Tectona grandis"};
    public static String[] THENI_BLACK_CAT = {"Timber Trees",
            "Pulpwood Trees", "Pulpwood Trees",
            "Matchwood Trees", "Matchwood Trees", "Matchwood Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Biofuel Species", "Biofuel Species", "Biofuel Species",
            "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees"};

    //Tree Type eight,Plains; 35
    public static String[] THIRUVALLUR_SOIL_TYPE = {"Black Soil", "Red Sandy Loam  Soil"};
    public static String[] THIRUVALLUR_BLACK = {"Tectona grandis", "Gmelina arborea",
            "Hardwickia binnata", "Eucalyptus tereticornis", "Casuarina equisetifolia", "Ailanthus excelsa", "Ailanthus triphysa/A.Malabarica", "Melia dubia", "Cassia siamea", "Erythrina indica",
            "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia holosericea", "Pongamia pinnata",
            "Cocos nucifera", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica", "Artocarpus heterophyllus", "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Alstonia scholaris", "Melia dubia",
            "Albizia lebbeck", "Azadirichta indica", "Cassia fistula", "Gmelina arborea", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Tamarindus indica", "Tectona grandis"};
    public static String[] THIRUVALLUR_BLACK_CAT = {"Timber Trees", "Timber Trees", "Timber Trees",
            "Pulpwood Trees", "Pulpwood Trees",
            "Matchwood Trees", "Matchwood Trees", "Matchwood Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Biofuel Species", "Biofuel Species", "Biofuel Species",
            "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees"};

    // Size 37
    //public static String[] THRIVALLUR_SOIL_TYPE = {"Alluvial Soil"};
    public static String[] THRIVALLUR_RED_SANDY = {"Tectona grandis", "Gmelina arborea", "Thespesia populnea", "Hardwickia binnata", "Pterocarpus santalinus",
            "Eucalyptus tereticornis", "Casuarina equisetifolia", "Bambusa vulgaris",
            "Ailanthus excelsa", "Melia dubia",
            "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia Auriculiformis", "Acacia holosericea", "Pongamia pinnata",
            "Cocos nucifera", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Artocarpus heterophyllus",
            "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Alstonia scholaris", "Melia dubia",
            "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Gmelina arborea", "Pongamia pinnata", "Syzgium cumini", "Thespesia populnea", "Tectona grandis"
    };
    public static String[] THRIVALLUR_RED_SANDY_CAT = {"Timber Trees", "Timber Trees", "Timber Trees", "Timber Trees", "Timber Trees",
            "Pulpwood Trees", "Pulpwood Trees", "Pulpwood Trees",
            "Matchwood Trees", "Matchwood Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Biofuel Species", "Biofuel Species", "Biofuel Species", "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"

    };

    //Plains Eight Alluvial
// Size  42
    public static String[] THIRUVARUR_SOIL_TYPE = {"Alluvial Soil"};


    public static String[] THIRUVARUR_SOIL = {"Tectona grandis", "Gmelina arborea", "Dalbergia sissoo", "Thespesia populnea", "Hardwickia binnata",
            "Eucalyptus tereticornis", "Casuarina equisetifolia", "Bambusa bambos",
            "Ailanthus excelsa", "Ailanthus triphysa/A.Malabarica", "Melia dubia",
            "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia Auriculiformis", "Acacia holosericea", "Pongamia pinnata",
            "Cocos nucifera", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica", "Artocarpus heterophyllus",
            "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Anthocephalus cadamba", "Alstonia scholaris", "Melia dubia",
            "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Gmelina arborea", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Thespesia populnea", "Tamarindus indica", "Tectona grandis"

    };
    public static String[] THIRUVARUR_SOIL_CAT = {"Timber Trees", "Timber Trees", "Timber Trees", "Timber Trees", "Timber Trees",
            "Pulpwood Trees", "Pulpwood Trees", "Pulpwood Trees",
            "Matchwood Trees", "Matchwood Trees", "Matchwood Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Biofuel Species", "Biofuel Species", "Biofuel Species",
            "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"
    };
    //Low,Plains Coastal Plains

    public static String[] Thoothukkudi_SoilType = {"Red Sandy Loam  Soil", "Black Soil", "Coastal Alluvial Soil"};
    //Size 35
    public static String[] THOOTHUKUDI_RED_SANDY = {"Tectona grandis", "Thespesia populnea", "Hardwickia binnata",
            "Eucalyptus tereticornis", "Casuarina junghuhniana", "Bambusa vulgaris",
            "Ailanthus excelsa", "Ailanthus triphysa/A.Malabarica", "Melia dubia",
            "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia holosericea", "Pongamia pinnata",
            "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica",
            "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Alstonia scholaris", "Melia dubia",
            "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Thespesia populnea", "Tamarindus indica", "Tectona grandis"
    };
    public static String[] THOOTHUKUDI_RED_SANDY_CAT = {"Timber Trees", "Timber Trees", "Timber Trees",
            "Pulpwood Trees", "Pulpwood Trees", "Pulpwood Trees",
            "Matchwood Trees", "Matchwood Trees", "Matchwood Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Biofuel Species", "Biofuel Species", "Biofuel Species",
            "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"
    };


    //Size 31
    public static String[] THOOTHUKUDI_BLACK = {"Tectona grandis",
            "Casuarina junghuhniana", "Bambusa vulgaris",
            "Ailanthus excelsa", "Ailanthus triphysa/A.Malabarica", "Melia dubia",
            "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia holosericea", "Pongamia pinnata",
            "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica",
            "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Alstonia scholaris", "Melia dubia",
            "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Tamarindus indica", "Tectona grandis"
    };
    public static String[] THOOTHUKUDI_BLACK_CAT = {"Timber Trees",
            "Pulpwood Trees", "Pulpwood Trees",
            "Matchwood Trees", "Matchwood Trees", "Matchwood Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Biofuel Species", "Biofuel Species", "Biofuel Species",
            "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"


    };


//Size 30

    public static String[] THOOTHUKUDI_COASTAL = {"Tectona grandis", "Thespesia populnea",
            "Casuarina junghuhniana", "Bambusa vulgaris",
            "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia holosericea", "Pongamia pinnata",
            "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica",
            "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Alstonia scholaris", "Melia dubia",
            "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Thespesia populnea", "Tamarindus indica", "Tectona grandis"

    };
    public static String[] THOOTHUKUDI_COASTAL_CAT = {"Timber Trees", "Timber Trees",
            "Pulpwood Trees", "Pulpwood Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Biofuel Species", "Biofuel Species", "Biofuel Species",
            "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"


    };
    //Low,
    //Size   41  41
    public static String[] Tiruchirappalli_Terrain = {"Plains", "Less Sloppy Hills"};
    public static String[] Tiruchirappalli_SoilType = {"Red Sandy Loam  Soil", "Black Soil", "Alluvial Soil"};


    public static String[] TIRUCHIRAPPALLI_RED_SANDY = {"Tectona grandis", "Gmelina arborea", "Thespesia populnea", "Hardwickia binnata",
            "Eucalyptus tereticornis", "Casuarina equisetifolia", "Bambusa bambos",
            "Ailanthus excelsa", "Ailanthus triphysa/A.Malabarica", "Melia dubia",
            "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia Auriculiformis", "Acacia holosericea", "Pongamia pinnata",
            "Cocos nucifera", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica", "Artocarpus heterophyllus",
            "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Anthocephalus cadamba", "Alstonia scholaris", "Melia dubia",
            "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Gmelina arborea", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Thespesia populnea", "Tamarindus indica", "Tectona grandis"
    };
    public static String[] TIRUCHIRAPPALLI_RED_SANDY_CAT = {"Timber Trees", "Timber Trees", "Timber Trees", "Timber Trees",
            "Pulpwood Trees", "Pulpwood Trees", "Pulpwood Trees",
            "Matchwood Trees", "Matchwood Trees", "Matchwood Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Biofuel Species", "Biofuel Species", "Biofuel Species",
            "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",

    };

    //Size 35 35
    public static String[] TIRUCHIRAPPALLI_BLACK = {"Tectona grandis", "Gmelina arborea", "Hardwickia binnata",
            "Eucalyptus tereticornis", "Casuarina equisetifolia",
            "Ailanthus excelsa", "Ailanthus triphysa/A.Malabarica", "Melia dubia",
            "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia holosericea", "Pongamia pinnata",
            "Cocos nucifera", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica", "Artocarpus heterophyllus",
            "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Alstonia scholaris", "Melia dubia",
            "Albizia lebbeck", "Azadirichta indica", "Cassia fistula", "Gmelina arborea", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Tamarindus indica", "Tectona grandis"

    };
    public static String[] TIRUCHIRAPPALLI_BLACK_CAT = {"Timber Trees", "Timber Trees", "Timber Trees",
            "Pulpwood Trees", "Pulpwood Trees",
            "Matchwood Trees", "Matchwood Trees", "Matchwood Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Biofuel Species", "Biofuel Species", "Biofuel Species",
            "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"

    };

    //Size  42  42


    public static String[] TIRUCHIRAPPALLI_ALLUVIAL = {"Tectona grandis", "Gmelina arborea", "Dalbergia sissoo", "Thespesia populnea", "Hardwickia binnata",
            "Eucalyptus tereticornis", "Casuarina", "Bambusa bambos",
            "Ailanthus excelsa", "Ailanthus triphysa/A.Malabarica", "Melia dubia",
            "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia Auriculiformis", "Acacia holosericea", "Pongamia pinnata",
            "Cocos nucifera", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica", "Artocarpus heterophyllus",
            "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Anthocephalus cadamba", "Alstonia scholaris", "Melia dubia",
            "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Gmelina arborea", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Thespesia populnea", "Tamarindus indica", "Tectona grandis"

    };
    public static String[] TIRUCHIRAPPALLI_ALLUVIAL_CAT = {"Timber Trees", "Timber Trees", "Timber Trees", "Timber Trees", "Timber Trees",
            "Pulpwood Trees", "Pulpwood Trees", "Pulpwood Trees",
            "Matchwood Trees", "Matchwood Trees", "Matchwood Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Biofuel Species", "Biofuel Species", "Biofuel Species",
            "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"

    };
    //Low
    public static String[] Tirunelveli_SoilType = {"Deep Red Soil", "Red Sandy Loam  Soil", "Black Soil", "Coastal Alluvial Soil"};
    public static String[] Tirunelveli_Terrain = {"Plains", "Coastal Plains"};

    //Size 27 27

    public static String[] TIRUNELVELLI_DEEP = {"Tectona grandis", "Dalbergia latifolia",
            "Eucalyptus tereticornis", "Casuarina junghuhniana",
            "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Gliricidia sepium", "Acacia holosericea", "Pongamia pinnata",
            "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica",
            "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Alstonia scholaris",
            "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Dalbergia latifolia", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Tamarindus indica", "Tectona grandis"
    };
    public static String[] TIRUNELVELLI_DEEP_CAT = {"Timber Trees", "Timber Trees",
            "Pulpwood Trees", "Pulpwood Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Biofuel Species", "Biofuel Species", "Biofuel Species",
            "Pencilwood / Plywood Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"
    };

    //Size 35 35
    public static String[] TIRUNELVELI_RED_SANDY = {"Tectona grandis", "Thespesia populnea", "Hardwickia binnata",
            "Eucalyptus tereticornis", "Casuarina equisetifolia", "Bambusa bambos",
            "Ailanthus excelsa", "Ailanthus triphysa/A.Malabarica", "Melia dubia",
            "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia holosericea", "Pongamia pinnata",
            "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica",
            "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Alstonia scholaris", "Melia dubia",
            "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Thespesia populnea", "Tamarindus indica", "Tectona grandis"
    };
    public static String[] TIRUNELVELI_RED_SANDY_CAT = {"Timber Trees", "Timber Trees", "Timber Trees",
            "Pulpwood Trees", "Pulpwood Trees", "Pulpwood Trees",
            "Matchwood Trees", "Matchwood Trees", "Matchwood Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Biofuel Species", "Biofuel Species", "Biofuel Species",
            "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",

    };

    //Size 31  31;
    public static String[] TIRUNELVELLI_BLACK = {"Tectona grandis",
            "Casuarina junghuhniana", "Bambusa vulgaris",
            "Ailanthus excelsa", "Ailanthus triphysa/A.Malabarica", "Melia dubia",
            "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia holosericea", "Pongamia pinnata",
            "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica",
            "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Alstonia scholaris", "Melia dubia",
            "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Tamarindus indica", "Tectona grandis"
    };
    public static String[] TIRUNELVELLI_BLACK_CAT = {"Timber Trees",
            "Pulpwood Trees", "Pulpwood Trees",
            "Matchwood Trees", "Matchwood Trees", "Matchwood Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Biofuel Species", "Biofuel Species", "Biofuel Species",
            "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"
    };

    //Size 30 30
    public static String[] TIRUNELVELI_COASTAL = {"Tectona grandis", "Thespesia populnea",
            "Casuarina junghuhniana", "Bambusa vulgaris",
            "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia holosericea", "Pongamia pinnata",
            "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica",
            "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Alstonia scholaris", "Melia dubia",
            "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Thespesia populnea", "Tamarindus indica", "Tectona grandis"

    };
    public static String[] TIRUNELVELI_COASTAL_CAT = {"Timber Trees", "Timber Trees",
            "Pulpwood Trees", "Pulpwood Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Biofuel Species", "Biofuel Species", "Biofuel Species",
            "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"
    };
    //Size 38  38
    public static String[] TIRUPPUR_SOIL_TYPE = {"Red Loamy Soil", "Black Soil"};
    public static String[] TIRUPPUR_RED_LOAMY = {"Tectona grandis", "Gmelina arborea", "Thespesia populnea", "Hardwickia binnata",
            "Eucalyptus tereticornis", "Casuarina junghuhniana", "Bambusa vulgaris",
            "Ailanthus excelsa", "Ailanthus triphysa/A.Malabarica", "Melia dubia",
            "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia holosericea", "Pongamia pinnata",
            "Cocos nucifera", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica",
            "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Anthocephalus cadamba", "Alstonia scholaris", "Melia dubia",
            "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Gmelina arborea", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Thespesia populnea", "Tamarindus indica", "Tectona grandis"};
    public static String[] TIRUPPUR_RED_LOAMY_CAT = {"Timber Trees", "Timber Trees", "Timber Trees", "Timber Trees",
            "Pulpwood Trees", "Pulpwood Trees", "Pulpwood Trees",
            "Matchwood Trees", "Matchwood Trees", "Matchwood Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Biofuel Species", "Biofuel Species", "Biofuel Species",
            "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"
    };

    //Size 36 36
    public static String[] TIRUPPUR_BLACK = {"Tectona grandis", "Gmelina arborea", "Thespesia populnea",
            "Eucalyptus tereticornis", "Casuarina junghuhniana", "Bambusa vulgaris",
            "Ailanthus excelsa", "Ailanthus triphysa/A.Malabarica", "Melia dubia",
            "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia holosericea", "Pongamia pinnata",
            "Cocos nucifera", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica",
            "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica", "Alstonia scholaris", "Melia dubia",
            "Albizia lebbeck", "Azadirichta indica", "Cassia fistula", "Gmelina arborea", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Thespesia populnea", "Tamarindus indica", "Tectona grandis"};
    public static String[] TIRUPPUR_BLACK_CAT = {"Timber Trees", "Timber Trees", "Timber Trees",
            "Pulpwood Trees", "Pulpwood Trees", "Pulpwood Trees",
            "Matchwood Trees", "Matchwood Trees", "Matchwood Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Biofuel Species", "Biofuel Species", "Biofuel Species",
            "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"
    };
    public static String[] TIRUVANNAMALAI_SOIL_TYPE = {"Red Sandy Loam  Soil", "Red Loamy Soil"};
    public static String[] TIRUVANNAMALAI_RED_SANDY = {"Tectona grandis", "Gmelina arborea", "Thespesia populnea", "Hardwickia binnata", "Pterocarpus santalinus",
            "Eucalyptus tereticornis", "Casuarina equisetifolia", "Bambusa vulgaris",
            "Ailanthus excelsa", "Melia dubia",
            "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia Auriculiformis", "Acacia holosericea", "Pongamia pinnata",
            "Cocos nucifera", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Artocarpus heterophyllus",
            "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Alstonia scholaris", "Melia dubia",
            "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Gmelina arborea", "Pongamia pinnata", "Syzgium cumini", "Thespesia populnea", "Tectona grandis"
    };
    public static String[] TIRUVANNAMALAI_RED_SANDY_CAT = {"Timber Trees", "Timber Trees", "Timber Trees", "Timber Trees", "Timber Trees",
            "Pulpwood Trees", "Pulpwood Trees", "Pulpwood Trees",
            "Matchwood Trees", "Matchwood Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Biofuel Species", "Biofuel Species", "Biofuel Species", "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"

    };
    //Size 24  24
    public static String[] TIRUVANNAMALAI_RED_LOAMY = {"Cassia siamea", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia Auriculiformis", "Acacia holosericea", "Pongamia pinnata",
            "Cocos nucifera", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica", "Artocarpus heterophyllus",
            "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica", "Alstonia scholaris",
            "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini"};
    public static String[] TIRUVANNAMALAI_RED_LOAMY_CAT = {"Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Biofuel Species", "Biofuel Species", "Biofuel Species",
            "Pencilwood / Plywood Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"
    };
    public static String[] VELLORE_SOIL_TYPE = {"Red Loamy Soil", "Clayey Loam Soil"};
    //Size 24 24
    public static String[] VELLORE_RED_LOAMY = {"Cassia siamea", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia Auriculiformis", "Acacia holosericea", "Pongamia pinnata",
            "Cocos nucifera", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica", "Artocarpus heterophyllus",
            "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica", "Alstonia scholaris",
            "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini"};
    public static String[] VELLORE_RED_LOAMY_CAT = {"Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Biofuel Species", "Biofuel Species", "Biofuel Species",
            "Pencilwood / Plywood Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"
    };
    //Size 26 26
    public static String[] VELLORE_CLAYEY = {"Thespesia populnea",
            "Eucalyptus tereticornis",
            "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia Auriculiformis", "Acacia holosericea", "Pongamia pinnata",
            "Cocos nucifera", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica",
            "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica", "Alstonia scholaris",
            "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Thespesia populnea"};
    public static String[] VELLORE_CLAYEY_CAT = {"Timber Trees",
            "Pulpwood Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Biofuel Species", "Biofuel Species", "Biofuel Species",
            "Pencilwood / Plywood Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"
    };
    public static String[] VILUPPURAM_SOIL_TYPE = {"Red Sandy Loam  Soil"};
    //Size 36  36
    public static String[] VILUPPURAM_RED_SANDY = {"Tectona grandis", "Gmelina arborea", "Thespesia populnea", "Hardwickia binnata",
            "Eucalyptus tereticornis", "Casuarina equisetifolia", "Bambusa vulgaris",
            "Ailanthus excelsa", "Melia dubia",
            "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia Auriculiformis", "Acacia holosericea", "Pongamia pinnata",
            "Cocos nucifera", "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Artocarpus heterophyllus",
            "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Alstonia scholaris", "Melia dubia",
            "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Gmelina arborea", "Pongamia pinnata", "Syzgium cumini", "Thespesia populnea", "Tectona grandis"};
    public static String[] VILUPPURAM_RED_SANDY_CAT = {"Timber Trees", "Timber Trees", "Timber Trees", "Timber Trees",
            "Pulpwood Trees", "Pulpwood Trees", "Pulpwood Trees",
            "Matchwood Trees", "Matchwood Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Biofuel Species", "Biofuel Species", "Biofuel Species",
            "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"};


    //Size 31  31
    public static String[] VIRUDHUNAGAR_SOIL_TYPE = {"Black Soil"};
    public static String[] VIRUDHUNAGAR_BLACK = {"Tectona grandis",
            "Casuarina junghuhniana", "Bambusa vulgaris",
            "Ailanthus excelsa", "Ailanthus triphysa/A.Malabarica", "Melia dubia",
            "Cassia siamea", "Erythrina indica", "Leucaena leucocephala", "Sesbania grandiflora", "Gliricidia sepium", "Acacia holosericea", "Pongamia pinnata",
            "Murraya koenigii", "Emblica officinalis", "Azadirichta indica", "Tamarindus indica",
            "Jatropha curcas", "Pongamia pinnata", "Azadirichta indica",
            "Alstonia scholaris", "Melia dubia",
            "Albizia lebbeck", "Azadirichta indica", "Basia latifolia", "Cassia fistula", "Pletophorum pterocarpum", "Pongamia pinnata", "Syzgium cumini", "Tamarindus indica", "Tectona grandis"
    };

    public static String[] VIRUDHUNAGAR_BLACK_CAT = {"Timber Trees",
            "Pulpwood Trees", "Pulpwood Trees",
            "Matchwood Trees", "Matchwood Trees", "Matchwood Trees",
            "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees", "Firewood, Fuelwood and Fodder Trees",
            "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)", "Other Beneficial Trees (NTFP)",
            "Biofuel Species", "Biofuel Species", "Biofuel Species",
            "Pencilwood / Plywood Trees", "Pencilwood / Plywood Trees",
            "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees", "Industrial / Institutional Plantation Trees"
    };

    public static String[] RAIN_FALLS_LOW = {"Low"};
    public static String[] RAIN_FALLS_HIGH = {"High"};
    public static String[] RAIN_FALLS_MODERATE = {"Moderate"};

    public static String[] TERRAIN_PL_HILLS = {"Plains / Hills"};
    public static String[] TERRAIN_PLAINS = {"Plains"};
    public static String[] TERRAIN_HILLS = {"Hills"};
    public static String[] TERRAIN_PL_LESS = {"Plains / Less Sloppy Hills"};
    public static String[] TERRAIN_CO_PLAN = {"Plains / Coastal Pains"};


    public static String[] DESTRICT_NAME = {"Chennai", "Coimbatore", "Cuddalore", "Dharmapuri", "Dindigul", "Erode", "Kanniyakumari", "Kanchipuram", "Karur", "Krishnagiri", "Madurai", "Nagapattinam", "Namakkal", "Pudukkottai", "Ramanathapuram", "Salem",
            "Sivagangai", "Thanjavur", "Theni", "The Nilgiris", "Thoothukkudi", "Tiruchirappalli", "Tirunelveli", "Tiruvallur", "Tiruvannamalai", "Tiruvarur", "Vellore", "Villupuram", "Virudhunagar"};

    public static String[] CONTACT_ADDRESS = {"DEPUTY CONSERVATOR OF FORESTS STATE FOREST RESEARCH INSTITUTE KOLAPAKKAM (VIA) VANDALUR CHENNAI 600 048.",
            "DIVISIONAL FOREST OFFICER SOCIAL FORESTRY DIVISION TAMIL NADU FOREST ACADEMY CAMPUS, R.S.PURAM COIMBATORE 641 002",
            "REGIONAL MANAGER TAMIL NADU FOREST PLANTATION CORPORATION LIMITED 110, LUCAS STREETVRIDHACHALAM CUDDALORE DISTRICT. TAMIL NADU, SOUTH INDIA. PIN–606 001.",
            "DIVISIONAL FOREST OFFICER MODERN NURSERY DIVISION, COLLECTOR OFFICE CAMPUS DHARMAPURI - 1", "DISTRICT FOREST OFFICER,DINDIGUL DIVISION, VELUNACHIAR VALAGAM, DINDIGUL- 624 004. dfodgl@tn.nic.in",
            "DISTRICT FOREST OFFICER ERODE FOREST DIVISION, MULLIAGAM ROJA NAGAR, VEERAPPAN CHATHRAM ERODE 638 004. dfoerd@tn.nic.in",
            "DISTRICT FOREST OFFICER,KANYAKUMARI DIVISION,175, COLLEGE ROAD,MUNICIPALITY CAMPUS, NAGERCOIL -629 001. dfokkm@tn.nic.in",
            "DIVISIONAL FOREST OFFICER,SOCIAL FORESTRY DIVISION, A/35, ALAGESAN Nagar, CHINGLEPUT 603 001",
            "DIVISIONAL FOREST OFFICER CRASH PLANTATION DIVISION 37/1, MGR NAGAR (1ST FLOOR)KARUR 639 001 dfokar@tn.nic.in",
            "DIVISIONAL FOREST OFFICER,RURAL FUELWOOD DIVISION1/17, WESTLINE COLONYKRISHNAGIRI – 635 115",
            "DIVISIONAL FOREST OFFICER,SOCIAL FORESTRY DIVISION, NO:5,VADAKKU STREET,SINGARAYAR NAGAR COLONY, MADURAI - 625 002.",
            "WILDLIFE WARDEN,DISTRICT COLLECTORATE CAMPUS,329, 3rd FLOOR,NAGAPATTINAM - 611 001 wlife-kmb@sancharnet.in",
            "DISTRICT FOREST OFFICER INTERFACE FORESTRY DIVISION NO:9, CO-OPERATIVE COLONY NAMAKKAL -637 401",
            "DIVISIONAL FOREST OFFICER,SOCIAL FORESTRY DIVISION,NEAR HEAD POST OFFICE, PUDUKOTTAI -622 003. dfopdk@tn.nic.in",
            "DIVISIONAL FOREST OFFICER,SOCIAL FORESTRY DIVISION,JAWAN BHAVAN SENIKKARAI, RAMANATHAPURAM- 623 535. dformd@tn.nic.",
            "DISTRICT FOREST OFFICER SALEM DIVISION, GANDHI SALAI SALEM 636 007 dfoslm@tn.nic.in",
            "DIVISIONAL FOREST OFFICER,SOCIAL FORESTRY DIVISION,13, MASTER PLAN CAMPUS, SIVAGANGA -623 569. dfovnr@tn.nic.in",
            "DISTRICT FOREST OFFICER THANJAVUR DIVISION, ANNAI INDIRA NAGAR,PUDUKOTTAI ROAD, THANJAVUR -613 005. dfotnj@tn.nic.in",
            "DISTRICT FOREST OFFICER,THENI DIVISION,KRR NAGAR, HOUSING BOARD COLONY,THENI -626 531. dfothn@tn.nic.in",

            "DISTRICT FOREST OFFICER NILGIRIS SOUTH DIVISION, MOUNT STUART HILL UDHAGAI 643 101 NILGIRIS  E-mail: dfosouth@sancharnet.in",

            "DIVISIONAL FOREST OFFICER,SOCIAL FORESTRY DIVISION,1 13-4/1, ETTAYAPURAM ROAD,POLEPETTAI, THOOTHUKUDI -628 002. dfotut@tn.nic.in",
            "DIVISIONAL FOREST OFFICER,SOCIAL FORESTRY DIVISION, 2ND FLOOR, SHOPPING COMPLEX, TAMIL NADU HOUSING BOARD BUILDING, SALAI ROAD, URAYUR, TRICHY 623 535",
            "DIVISIONAL FOREST OFFICER,SOCIAL FORESTRY DIVISION, TIRUNELVELI –627 007",
            "DIVISIONAL FOREST OFFICER INTERFACE FORESTRY DIVISION G.V.T. NAIDU SALAI JAYA NAGARTIRUVALLUR E-mail – dfo@tlr.tn.nic.in",
            "DIVISIONAL FOREST OFFICER,AFFORESTATION DIVISION,164, ADHISESHA NAGAR,(NEAR BALASUBRAMANYA THEATRE) TIRUVANNAMALAI.E-mail: affdivn@sancharnet.in",
            "DISTRICT FOREST OFFICER,THANJAVUR DIVISION, ANNAI INDIRA NAGAR, PUDUKOTTAI ROAD, THANJAVUR 613 005E-mail: dfotnj@tn.nic.in",
            "DIVISIONAL FOREST OFFICER SOCIAL FORESTRY DIVISION COLLECTOR BUILDING B-BLOCK 6TH FLOOR SATHUVACHCHARI VELLORE 632 009 E-mail: dfosf@vlr.tn.nic.in",
            "DIVISIONAL FOREST OFFICER,SOCIAL FORESTRY DIVISION,2POONTHOTTAM SALAI VILLUPURAM -605 602",
            "DIVISIONAL FOREST OFFICER, SOCIAL FORESTRY DIVISION , NO:16,14,20 C.C. SALAI RAJAJI NAGAR, M.S.F. COLONY, VIRUDHUNAGAR – 1. E-mail – dfovnr@tn.nic.in",
    };

    public static String[] CONTACT_NO = {"044-22750297", "0422-2440905", "04143-260322", "04342-230003", "0451-2460470", "0424-2291722", "04652-232205", "04114-231746", " 04324-232229", "04343-225625",
            "0452-2562912", "04365-253092", "04286-229369", "04322-231995", "04567-220693", "0427-2415097", "04575-240329", "04362-227308", "04546-252552", "0423-2444083", "0461-2346600",
            "0431-2760930", "0462-2531806", "044-27660487", "04175-237972", "04362-227308", "0416-2252852", "04146-222148", "04562-280466"};

    public static String[] OTHER_CONTACT = {"Institute of Forest Genetics and Tree Breeding (IFGTB)", "Forest College & Research Institute"};
    public static String[] OTHER_CONTACT_ADDRESS = {"The Director Institute of Forest Genetics and Tree Breeding (Indian Council of Forestry Research and Education) P.B. No. 1061, R.S. Puram P.O., Coimbatore-641002, INDIA email: dir_ifgtb@icfre.org",
            "Dean Forest College & Research Institute Mettupalayam - 641301 Coimbatore District.email : deanformtp@tnau.ac.in"};

    public static String[] OTHER_CONTACT_NO = {"0422-2484100, 2484101", "04254-222010,227418"};


    public static String[] title = {"Acidic Soil", "Agroforestry", "Alley Cropping", "Alluvial Soil", "Avenue ", "Black Cotton Soil", "Block Plantation ", "Clay Soil ", "Clay Loam/Clayey Loam "
            , "Cleaning", "Clearing ", "Climatic Factors ", "Clone ", "Clump", "Collar", "Commercial Timber", "Competition ", "Compost ", "Contour Cropping", "Controlled Burning", "Coppicing", "Coppice Seedling ", "Coppice Seedling "
            , "Crown", "Cutting", "Degraded Soil", "Direct Sowing ", "Drip irrigation ", "Environment", "Erosion", "Establishment ", "Farm yard manure (FYM) "
            , "Final Felling ", "Fertilizer ", "Fire Protection", "Farm Forestry ", "Germination", "Graft", "Grazing ", "Green Manuring ", "Group Planting ", "Hedgerow ", "Increment ", "Intercropping ",
            "Lateritic Soil ", "Light Requirement", "Line Sowing ", "Loam ", "Mulch", "Nitrogen Fixation ", "Nursery", "Nursery Seedling ", "Nursery Transplant ", "Nursery Stock", "Plantation", "Pit Planting", "Pole ", "Pollard", "Pretreatment", "Pruning", "Pulpwood", "Regeneration", "Rhizome ", "Root Graft "
            , "Root Sucker", "Rotation", "Sapling", "Scion", "Seedling ", "Shade Demander", "Shelterbelt", "Shifting Cultivation ", "Soil Conservation", "Soil Working", "Spacing ", "Strip Felling ", "Strip Sowing "
            , "Stock", "Stump", "Stump Planting", "Sub-Tropical Climate", "Tending", "Thinning", "Timber Sawn", "Transplant", "Tropical Climate", "Weeding", "Windbreak", "Yield"};

    public static String[] Description = {"     A soil in which the pH value of the soil solution is less than 6.7."
            , "       The integration of agriculture and/or farming with forestry so the land can simultaneously be used for more than one purpose."
            , "       A method of planting in which rows of trees are Interspersed with rows of crops, improving the soil and providing nutrients, particularly nitrogen, to the crops."
            , "       A secondary soil derived essentially from flood plain material, immature and without horizons, though strata may be present owing to the varying nature of the material deposited by the water at different times."
            , "       A broad roadway lined with trees."
            , "       A tropical or sub-tropical black plastic clay soil, often developed from basic igneous rocks or in depressions, and sometimes found in alluvial sites. It has low organic matter content and owes its has low organic matter content and owes its black colour to other constituents."
            , "       Tree plantation in compact blocks of more than 0.1 ha on lands outside forest area."
            , "       The finest soil particles, under 0.002 mm. in diameter."
            , "       A heavy soil intermediate in texture between clay and  loam."
            , "       A tending operation done in a sapling crop, involving the removal or topping of inferior growth including individuals of the favoured species, climber, etc. when they are interfering with the better grown individuals of the favoured species."
            , "       An open space in the forest, due to clearing of growth."
            , "       Light, atmospheric temperature, pressure and humidity, winds and other features of climate - regional, local and seasonal-that influence vegetation."
            , "       A group of individuals derived from a common parent by  asexual reproduction."
            , "       The aggregate of stems issuing from the same root/ rhizome system or stool with particular reference to bamboo's and the larger grasses."
            , "       The portion of a plant which marks the transition between stem and root, sometimes marked by a slight swelling."
            , "       The volume under bark of the commercial bole."
            , "       The struggle for the available food, light and moisture, which takes place among species and individuals in an assemblage of plants."
            , "       A mixture of vegetable materials, which has been allowed to rot, often with the addition of animal and / or mineral products;  used as a soil improver."
            , "       The cultivation of crops in strips along the contours of a slope."
            , "       Any deliberate use of fire whereby burning is restricted to a predetermined area and intensity."
            , "       To fell trees near ground level with a view to their producing coppice shoots."
            , "       Coppice shoots arising from the base of seedling that have been cut or burnt back."
            , "       A shoot arising from an adventitious bud at the base of a woody plant that has been cut near the ground or burnt back. Sometimes (incorrectly) used to include root suckers."
            , "       The upper branchy part of a tree above the bole."
            , "       A short length of stem branch or root placed in the soil, or other medium, in order that it may develop into a plant."
            , "       A soil that has been subjected to stronger leaching than the type soils of the area, hence lower in fertility."
            , "       Sowing seed directly on an area where a crop is to be raised, as opposed to sowing in a nursery."
            , "       An irrigation method that saves water and fertilizer by allowing water to dripslowly to the roots of plants, either onto the soil surface or directly onto the root zone, through a network of valves, pipes, tubing, and emitters."
            , "       All the biotic factor and aboitic factors of a site."
            , "       The removal of soil and rock material by water, wind and gravity; generally refers to accelerated."
            , "       Development of a new, crop, naturally or assisted, to a stage where the young Regeneration, natural or artificial, is considered safe from normal adverse influences such as frost, drought or weeds, and no longer needs special protection or tending operations other that cleaning, thinning and pruning."
            , "       Farm yard manure is a decomposed mixture of Cattle  dung and urine with straw and litter used as bedding material and residues from the fodder fed to the cattle."
            , "       The removal of the last seed or shelter trees after regeneration has been effected under a shelter wood system."
            , "       Any organic or inorganic material of natural or synthetic origin which is added to a soil in an attempt to provided plant nutrients."
            , "       All activities concerned with protection of a forest area from damage by fire; comprises prevention, detection,presupperssion and suppression."
            , "       The practice of forestry in all its aspects on farm or village lands, generally integrated with other farm operation."
            , "       The resumption of growth of a seed or spore. Usually recognized by rupture of the seed coat or spore wall ad appearance of the radical, hypocotyle or thallus."
            , "       Scion-of one plant to a stock, usually rooted, which is another plant or another portion of the same plant, with the object of securing vegetative union between the two; the scion being detached from its parent plant either before or after the operation. Also applied to the composite individual so obtained."
            , "       The eating of any kind of standing vegetation by domestic livestock or wild animals. Sometimes limited to the eating of herbage in contrast with browsing."
            , "       Turning a crop under, while green or soon after maturity, for the purpose of soil improvement."
            , "       Planting trees, etc., in groups. Often used for improving natural forest of scrub by planting groups of valuable species at wide intervals."
            , "       A row of bushes or trees forming a hedge."
            , "       The increase in girth, diameter, basal area, height, volume, quality, price or value of individual trees or crops during a given period."
            , "       A multiple cropping practice involving growing two or more crops in proximity. The most common goal of intercropping is to produce a greater yield on a given piece of land by making use of resources that would otherwise not be utilized by a single crop."
            , "       A soil characterized by the presence of laterite on the durface or the upper layers. Found in humid, tropical climates, as on the west coast and ghats of peninsular India"
            , "       The amount of light necessary for the satisfactory development of a species."
            , "       Sowing in drills or single lines as distinguished from sowing in strips."
            , "       A soil composed of sand, silt and clay in such proportions that properties of the soil are not dominated by any one of them. "
            , "       A protective covering, usually of organic matter such as leaves, straw, or peat, placed around plants to prevent the evaporation of moisture."
            , "       The conversion of elemental nitrogen to organic combinations, or to forms readily utilizable in biological processes, by nitrogen-fixing micro-organism. When brought about by bacteria in the root nodules of leguminous plants it is spoken of as symbiotic; if by free-living micro-organisms acting independently, it is referred to as non-symbiotic-fixation."
            , "       An area where plants are raised for eventual planting out; has ordinarily both seedling and transplant beds. Nurseries are either permanent or temporary."
            , "       A nursery in which seedlings only are raised, no transplanting being done."
            , "       A nursery into which seedlings are transplanted in preparation for forest planting. In Indian practice, called pricking out beds."
            , "       General, whatever is grown in a nursery for planting out; also plants supplied from a nursery."
            , "       A forest crop raised artificially, either by sowing or planting."
            , "       A method of planting in pits from which the soil has been dug-out and replaced or exchanged. Planting in prepared pits or natural depressions, with a view collecting and conserving moisture."
            , "       A young tree from the time when the lower branches begin to fall off to the time when the rate of height growth begins to slow down and crown expansion becomes marked."
            , "       A tree whose stem has been cut off in order to obtain a flush of shoots, Usually above the height to which browsing animals can reach."
            , "       Various treatments applied to seed prior to sowing in order to increase the rapidity or completeness of germination."
            , "       To remove live or dead branches or multiple leaders from standing trees for the improvement of the tree or its timber."
            , "       Wood cut or prepared primarily for manufacture into wood pulp."
            , "       The renewal of a forest crop by natural or artificial means; also the new crop so obtained."
            , "       A stem of rootlike appearance, lying on or under the ground, which roots and sends up shoots. Syn. Root-stock."
            , "       A graft between roots, generally natural."
            , "       A shoot rising from the root of a woody plant."
            , "       The planned number of years between the formation  or regeneration of a crop and its final felling . In the case of a selection forest, the average age at which a tree is considered mature for felling."
            , "       A young tree from the time when it reaches 3 feet in height till the lower branches begin to fall. A sapling is characterized by the absence of dead bark and its vigorous height growth."
            , "       Any unrooted portion of a plant used for grafting or budding on to a rooted stock."
            , "       A plant grown from seed, before it reaches the sapling stage. In nursery practice, it refers to the one that has not been pricked out in the nursery."
            , "       A species requiring at least in its early stages some degree of shade for its normal development."
            , "        A belt of trees and/or shrubs maintained for the purpose of shelter and wind, sun, snow-drift, etc. (Protective-belt) Shelterbelts are generally more extensive than windbreaks covering areas larger than a single farm", "       A method of cyclical cultivation, chiefly in vogue in the tropics, where cultivators cut the tree crop , burn it, and raise field crops for one or more years before moving on to another site and repeating the process.", "       The preservation of soil against deterioration and loss by using the land within its capabilities and applying those conservation practices that are necessary for its protection and improvement.", "       Loosening of the soil around plants by means of a pointed stick, hoe or similar implement to depths progressively increased with age, with a view to promoting soil acration and infiltration capacity and sonserving moisture.", "       The Distance between the trees put out in a plantation or standing in a crop.", "       A method of felling whereby the crop is removed in strip in one or more operations usually for purpose of regeneration.", "       Sowing thickly in narrow strips prepared for the purpose, usually at definite intervals from one another.", "       A rooted plant on which a scion is grafted.", "       The base of tree and its roots left in the ground after felling", "       Planting of stumps.", "       A climate hot in summer with a cool winter. ", "       Generally an operation carried out for the benefit of a forest crop, at any stage of its life; essentially covers operations on the crop itself and on competing vegetation, e.g., weeding, cleaning. thinning, and even improvement fellings; also pruning, climber cutting and girdling of unwanted growth, but not regeneration fellings, nor ground operations like soil working, drainage, irrigation and controlled burning.", "       A felling made in an immature stand for the purpose of improving the growth and form of the trees that remain, without permanently breaking the canopy", "       Timber sawn to size, with or without wane", "       A seedling after it has been moved one or more times in a nursery in contrast to a seedling planted out direct from the seedling.", "       A climate which is not very hot without a definite winter season.", "       A tending operation done in the seedling stage in a nursery or in a forest crop that involves the removal or cutting back of all weeds."
            , "       A narrow shelterbelt or other obstacle maintained against the wind. "
            , "       The volume or number of stems that can be removed annually or periodically, or the area over which fellings may pass annually or periodically, consistent with the attainment of the objects of management."};




    public static String checkLanguage(FragmentActivity fragmentActivity) {
        LanguageChange languageChange = new LanguageChange(fragmentActivity);
        String languages = languageChange.getStatus();

        if (languages.equals("1")) {
            //    Utils.setLocalLanguage("ta", fragmentActivity);
            return "1";
        } else {
            //  Utils.setLocalLanguage("en", fragmentActivity);
            return "2";
        }

    }

}