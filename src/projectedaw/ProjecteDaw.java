/*
 * to change this license header, choose license headers in project properties.
 * to change this template file, choose tools | templates
 * and open the template in the editor.
 */
package projectedaw;


import controladores.callesjpacontroller;
import controladores.comunidadesjpacontroller;
import controladores.cpostalesjpacontroller;
import controladores.municipiosjpacontroller;
import controladores.provinciasjpacontroller;
import controladores.poblacionesjpacontroller;
import entidades.calles;
import entidades.comunidades;
import entidades.cpostales;
import entidades.municipios;
import entidades.provincias;
import entidades.poblaciones;
import java.io.bufferedreader;

import java.io.inputstreamreader;
import java.net.httpurlconnection;
import java.net.url;
import java.text.simpledateformat;
import java.util.arraylist;
import java.util.date;
import javax.persistence.query;
import org.json.*;

/**
 *
 * @author Álex García
 */
public class projectedaw {

    /**
     * @param args the command line arguments
     */

    public static void main(string[] args) {

                //Instanciamos controladores de las entidades que vamos a utilizar de la BDD
                comunidadesjpacontroller ccomunidad = new comunidadesjpacontroller();
                municipiosjpacontroller cmunicipio = new municipiosjpacontroller();
                provinciasjpacontroller cprovincia = new provinciasjpacontroller();
                poblacionesjpacontroller cpoblacion = new poblacionesjpacontroller();
                cpostalesjpacontroller cpostal = new cpostalesjpacontroller();
                callesjpacontroller ccalles = new callesjpacontroller();

                //Creamos variables de tipo long que nos seviran para la creacion de los codigos individuales mas adelante
                long cpid= new long(0);
                long caid= new long(0);

                //indicamos url base de donde obtenemos los datos
		string url = "https://apiv1.geoapi.es/comunidades?type=json&key=16bc42be60c4daf079d0faab0ea979a7615843b4843a6103c05d9c320048cf1c&sandbox=0";
		string jsonstring = "";
		try {

                //llamamos la funcion del controlador que llama los datos y devuelve un json
			jsonstring = controlador.peticionhttpget(url);

			//Creamos un objeto JSON a partir de los datos que nos han llegado en formato string
			jsonobject obj = new jsonobject(jsonstring);

			//Extraemos el apartado "data" que es donde esta el contenido que nos interesa
			jsonarray arr = obj.getjsonarray("data");


                        //Para evitar posibles errores, borramos los datos existentes de la base de datos antes de introducir los nuevos
                        ccalles.deletetable();
                        cpostal.deletetable();
                        cpoblacion.deletetable();
                        cmunicipio.deletetable();
                        cprovincia.deletetable();
                        ccomunidad.deletetable();


                        //A continuación creamos un conjunto de buquels anidados que nos permitiran sacar la calle de cada comunidad autonoma pasando
                        //Pasando por Provincias, municipios, etc..
                        //Recorremos el array obtenido anteriormente (que su longitud equivale al toal de comunidade autonomas)
                        for (int i = 0; i < arr.length(); i++)
			{
			                //Instanciamos la entidad comunidad
                            comunidades com = new comunidades();



                            //asignamos parametros de la comunidad con los datos recibidos
                            com.setccom(arr.getjsonobject(i).getlong("ccom"));
                            com.setcom(arr.getjsonobject(i).getstring("com"));

                            //Creamos una nueva entrada en la bdd
                            ccomunidad.create(com);



                            //a continuacion repetimos preoceso con provincias y buscamos en la api las provincias que corresponden a la comunidad actual
                            //Editamos la url i le pasamos como parametro la comunidad autonoma en la que stamos actualmente recorriendo con el for padre para asi obtener todas las provincias de esa comunidad
                             url="https://apiv1.geoapi.es/provincias?ccom="+arr.getjsonobject(i).getstring("ccom")+"&?type=json&key=16bc42be60c4daf079d0faab0ea979a7615843b4843a6103c05d9c320048cf1c&sandbox=0";
                             jsonstring = controlador.peticionhttpget(url);
                             obj = new jsonobject(jsonstring);
                             jsonarray arr2 = obj.getjsonarray("data");


                             //Recorremos todas la provincias de la comunidad actual
                            for(int j = 0; j<arr2.length();j++){

                                //Instanciamos la entidad provincia
                                provincias pro = new provincias();

                                //Asignamos los parametros obtenidos
                                pro.setccom(arr2.getjsonobject(j).getlong("ccom"));
                                pro.setcpro(arr2.getjsonobject(j).getlong("cpro"));
                                pro.setpro(arr2.getjsonobject(j).getstring("pro"));

                                //Creamos la entrada de la provincia en la bdd
                                cprovincia.create(pro);

                                //ahora filtramos en la api los municipios de esta misma provincia en la que nos encontramos para asi obtener sus municipios, pasamos como parametro el codigo de provincia
                                url="https://apiv1.geoapi.es/municipios?cpro="+arr2.getjsonobject(j).getstring("cpro")+"&?type=json&key=16bc42be60c4daf079d0faab0ea979a7615843b4843a6103c05d9c320048cf1c&sandbox=0";
                                jsonstring = controlador.peticionhttpget(url);
                                obj = new jsonobject(jsonstring);
                                jsonarray arr3 = obj.getjsonarray("data");

                                //Recorremos los municipios de la provincia actual
                                for(int k =0; k<arr3.length();k++){
                                    //Instanciamos la entidad municipio
                                    municipios mun = new municipios();

                                    //Asignamos los parametros
                                    mun.setcmum(arr3.getjsonobject(k).getlong("cmum"));
                                    mun.setcpro(arr3.getjsonobject(k).getlong("cpro"));
                                    mun.setcun(arr3.getjsonobject(k).getstring("cun"));
                                    mun.setdmun50(arr3.getjsonobject(k).getstring("dmun50"));

                                    //Creamos el municipio
                                    cmunicipio.create(mun);


                                    //Seguimos repitiendo el proceso anterior ahora con los poblados, pasamos de parametros codigo de provincia y municipio
                                    url="https://apiv1.geoapi.es/poblaciones?cpro="+arr3.getjsonobject(k).getstring("cpro")+"&cmum="+arr3.getjsonobject(k).getstring("cmum")+"&?type=json&key=16bc42be60c4daf079d0faab0ea979a7615843b4843a6103c05d9c320048cf1c&sandbox=0";
                                    jsonstring = controlador.peticionhttpget(url);
                                    obj = new jsonobject(jsonstring);
                                    jsonarray arr4 = obj.getjsonarray("data");


                                    //Recorremos todos los poblados del municipio
                                    for(int z=0; z<arr4.length();z++){

                                        //Instanciamos el objeto de poblaciones
                                        poblaciones pob = new poblaciones();


                                        //Em este caso formateamos los datos obtenidos para que sigan un patron de longitud especifico
                                        string formatstringpob = string.format("%%0%dd", 7);
                                        string formatstringmun = string.format("%%0%dd", 5);
                                        string formatstringpro = string.format("%%0%dd", 3);


                                        string formattedstringpob = string.format(formatstringpob, arr4.getjsonobject(z).getlong("cun"));
                                        string formattedstringmun = string.format(formatstringmun, arr3.getjsonobject(k).getlong("cmum"));
                                        string formattedstringpro = string.format(formatstringpro, arr2.getjsonobject(j).getlong("cpro"));

                                        //Con los datos formateados, creamos una variable nueva que sea el conjunto de estas variables formateadas.
                                        //Con ello obtenemos un codigo de una longitud especifica, que cada X caracteres indican informacion del poblado y hacen un codigo unico
                                        string codigopob=arr.getjsonobject(i).getlong("ccom")+""+formattedstringpro+""+formattedstringmun+""+formattedstringpob;
                                        long poblong= new long (codigopob);

                                        //Añadimos los datos
                                        pob.setcmum(arr4.getjsonobject(z).getlong("cmum"));
                                        pob.setcpob(poblong);
                                        pob.setnentsi50(arr4.getjsonobject(z).getstring("nentsi50"));

                                        //Creamos entrada en la bdd
                                        cpoblacion.create(pob);


                                   //repetimos proceso con los codigos postales, pasamos por parametros la provincia, el municipio y poblado
                                    url="https://apiv1.geoapi.es/cps?cpro="+arr2.getjsonobject(j).getstring("cpro")+"&cmum="+arr3.getjsonobject(k).getstring("cmum")+"&cun="+arr4.getjsonobject(z).getstring("cun")+"&?type=json&key=16bc42be60c4daf079d0faab0ea979a7615843b4843a6103c05d9c320048cf1c&sandbox=0";
                                    jsonstring = controlador.peticionhttpget(url);
                                    obj = new jsonobject(jsonstring);
                                    jsonarray arr5 = obj.getjsonarray("data");


                                    for(int y=0; y<arr5.length(); y++){

                                        //Instanciamos la entidad de codigos postales
                                        cpostales cpo = new cpostales();

                                        //Asignamos valores
                                        cpo.setid(cpid);
                                        cpo.setcpob(poblong);
                                        cpo.setcp(arr5.getjsonobject(y).getlong("cpos"));

                                        //Creamos entrada en la bdd
                                        cpostal.create(cpo);


                                        //repetimos proceso con las calles pasando parametros de provincia, municipio, poblacion y codigo postal
                                    url="https://apiv1.geoapi.es/calles?cpro="+arr2.getjsonobject(j).getstring("cpro")+"&cmum="+arr3.getjsonobject(k).getstring("cmum")+"&cun="+arr4.getjsonobject(z).getstring("cun")+"&cpos="+arr5.getjsonobject(y).getstring("cpos")+"&?type=json&key=16bc42be60c4daf079d0faab0ea979a7615843b4843a6103c05d9c320048cf1c&sandbox=0";
                                    jsonstring = controlador.peticionhttpget(url);
                                    obj = new jsonobject(jsonstring);
                                    jsonarray arr6 = obj.getjsonarray("data");

                                    for(int x=0; x<arr6.length(); x++){

                                        //Instanciamos entidad calles
                                        calles call = new calles();

                                        //Asignamos valores
                                        call.setid(caid);
                                        call.setidpostal(cpid);
                                        call.setnvia(arr6.getjsonobject(x).getstring("nviac"));
                                        call.setcvia(arr6.getjsonobject(x).getlong("cvia"));
                                        call.settvia(arr6.getjsonobject(x).getstring("tvia"));

                                        //Creamos entrada en la bdd
                                        ccalles.create(call);

                                        //Aumentamos variable que se utiliza como id de calle
                                        caid++;
                                    }
                                    //Aumentamos variable que se utiliza como id de codigo postal
                                    cpid++;


                                    }

                                    }

                                }

                            }

			}

			//system.out.println("la respuesta es:\n" + respuesta);
		} catch (exception e) {
			// manejar excepción
			e.printstacktrace();
		}
	}


}