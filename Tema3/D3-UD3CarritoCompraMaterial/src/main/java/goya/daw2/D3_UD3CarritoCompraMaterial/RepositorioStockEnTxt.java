package goya.daw2.D3_UD3CarritoCompraMaterial;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/*
 Ejemplo de clase para mantener un mapa de productos, cargarlo de disco en el constructor
 y guardar en disco tras cada cambio.

 De esta manera vuestro controlador puede definir una variable global repositorioStock

 Al ser un mapa no permite repetidos
*/

public class RepositorioStockEnTxt {
	protected Map<String, Integer> stock;
	//protected final static String RUTA_FICHERO = "stock.data";
	protected final static String RUTA_FICHERO = "stock.txt";

	public RepositorioStockEnTxt() {
		super();
		stock = load();
	}

	public Map<String,Integer> load() {
		// TRAZA PARA SABER DÓNDE RESUELVE LA RUTA RELATIVA:
        // System.out.println((new File(RUTA_FICHERO)).getAbsolutePath());
		// intentamos leer el mapa del archivo, si no podemos quedará vacío
		Map<String,Integer> res = new HashMap<String,Integer>();
		try (
			//ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(RUTA_FICHERO))
			BufferedReader entrada = new BufferedReader(new FileReader(RUTA_FICHERO))
		) {
			//return (HashMap<String, Integer>) entrada.readObject();
			String línea;
			while ( (línea=entrada.readLine())!=null) {
				String[] campos = línea.split(":");
				if (campos.length>1) res.put(campos[0],Integer.parseInt(campos[1]));
				else System.err.println("Error leída línea no válida " + línea);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("Problema no existe archivo " + RUTA_FICHERO);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Problema leyendo archivo " + RUTA_FICHERO);
		}
		
		/*	
		} catch (ClassNotFoundException | IOException e) {
			System.err.println("Problema leyendo archivo " + RUTA_FICHERO);
             //return new HashMap<String,Integer>();
		}
		*/
		return res;
       
	}

	public boolean save() {
		try (
				// ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(RUTA_FICHERO))
				PrintWriter salida = new PrintWriter(new FileWriter(RUTA_FICHERO))
		) {
			// salida.writeObject(stock);
			for (Map.Entry valor : stock.entrySet()) {
				salida.printf("%s:%d\n",valor.getKey(),valor.getValue());
			}
			
		} catch (IOException e1) {
			System.err.println("ERROR GUARDANDO STOCK");
			return false;
		}
		return true;
	}

	public Map<String, Integer> getAll() {
		return stock;
	}

	public Integer getOne(String producto) {
		return stock.get(producto);
	}

	public void add(String producto, Integer cantidad) {
		stock.put(producto, cantidad);
		save();
	}

	public void del(String producto) {
	        stock.remove(producto);
	    	save();
		}

	public void modify(String producto, Integer cantidad) {
			stock.put(producto, cantidad);
	    	save();
		}

    // Prueba de concepto para ejecutar desde Eclipe o Visual
    ///*
	public static void main(String[] args) {
		RepositorioStockEnTxt repo = new RepositorioStockEnTxt();
		System.out.println(repo.getAll());
		//repo.add("Peras",10);
		//repo.add("Manzanas", 12);
		//repo.save();
		
	}
    //*/

}
