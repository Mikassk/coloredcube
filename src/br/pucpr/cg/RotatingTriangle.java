package br.pucpr.cg;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import br.pucpr.mage.Keyboard;
import br.pucpr.mage.Scene;
import br.pucpr.mage.Shader;
import br.pucpr.mage.Window;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;

/**
 * Essa classe demonstra como desenhar um triangulo na tela utilizando a OpenGL.
 */
public class RotatingTriangle implements Scene {
	private Keyboard keys = Keyboard.getInstance();

	/** Esta variável guarda o identificador da malha (Vertex Array Object) do triângulo */
	private int vao;

	/** Guarda o id do shader program, após compilado e linkado */
	private int shader;

	private float angle = 0;
	private float angleX = 0, angleY = 0;

	@Override
	public void init() {
		//Define a cor de limpeza da tela
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		//------------------------------
		//Carga/Compilação dos shaders
		//------------------------------

		shader = Shader.loadProgram("basic.vert", "basic.frag");

		//------------------
		//Criação da malha
		//------------------

		//O processo de criação da malha envolve criar um Vertex Array Object e associar a ele um buffer, com as
		// posições dos vértices do triangulo.

		//Criação do Vertex Array Object (VAO)
		vao = glGenVertexArrays();

		//Informamos a OpenGL que iremos trabalhar com esse VAO
		glBindVertexArray(vao);


		//Posição
		//Criação do buffer de posições
		//------------------------------
		//Criamos um array no java com as posições. Você poderia ter mais de um triângulo nesse mesmo
		//array. Para isso, bastaria definir mais posições.
		var vertexData = new float[] {
				0.5f,  0.5f, 0.5f,   //0
                -0.5f, -0.5f, 0.5f,  //1
                0.5f, -0.5f, 0.5f,   //2
                -0.5f, 0.5f, 0.5f,   //3
                0.5f, 0.5f, -0.5f,   //4
                -0.5f, 0.5f, -0.5f,  //5
                -0.5f, -0.5f, -0.5f, //6
                0.5f, -0.5f, -0.5f,   //7
                0.5f,  0.5f, 0.5f,   //8
                -0.5f, -0.5f, 0.5f,   //9
                0.5f, -0.5f, 0.5f,   //10
                -0.5f, 0.5f, 0.5f,   //11
                0.5f, 0.5f, -0.5f,   //12
                -0.5f, 0.5f, -0.5f,   //13
                -0.5f, -0.5f, -0.5f,   //14
                0.5f, -0.5f, -0.5f,   //15
                0.5f,  0.5f, 0.5f,   //16
                -0.5f, -0.5f, 0.5f,   //17
                0.5f, -0.5f, 0.5f,   //18
                -0.5f, 0.5f, 0.5f,   //19
                0.5f, 0.5f, -0.5f,   //20
                -0.5f, 0.5f, -0.5f,   //21
                -0.5f, -0.5f, -0.5f,   //22
                0.5f, -0.5f, -0.5f   //23
		};

		//Solicitamos a criação de um buffer na OpenGL, onde esse array será guardado
		var positions = glGenBuffers();
		//Informamos a OpenGL que iremos trabalhar com esse buffer
		glBindBuffer(GL_ARRAY_BUFFER, positions);

		//Damos o comando para carregar esses dados na placa de vídeo
		//o parametro GL_STATIC_DRAW indica que não mexeremos mais nos valores desses dados em nossa aplicação
		glBufferData(GL_ARRAY_BUFFER, vertexData, GL_STATIC_DRAW);

		//Procuramos o identificador do atributo de posição
		var aPosition = glGetAttribLocation(shader, "aPosition");

		//Chamamos uma função que associa as duas.
		glVertexAttribPointer(aPosition, 3, GL_FLOAT, false, 0, 0);

		//Informamos a OpenGL que iremos trabalhar com essa variável
		glEnableVertexAttribArray(aPosition);


		//Cor
		//Criação do buffer de posições
		//------------------------------
		//Criamos um array no java com as cores.
		var colorData = new float[] {
				1.0f, 0.0f, 0.0f,    //0
                1.0f, 0.0f, 0.0f,    //1
                1.0f, 0.0f, 0.0f,    //2
                1.0f, 0.0f, 0.0f,    //3

                0.0f, 1.0f, 0.0f,   //4
                0.0f, 1.0f, 0.0f,   //5
                0.0f, 1.0f, 0.0f,   //6
                0.0f, 1.0f, 0.0f,   //7

                0.0f, 1.0f, 1.0f,   //8
                0.5f, 0.5f, 0.5f,    //9
                0.0f, 0.0f, 1.0f,    //10
                0.5f, 0.5f, 0.5f,    //11

                0.0f, 1.0f, 1.0f,    //12
                0.5f, 0.5f, 0.5f,    //13
                0.0f, 0.0f, 1.0f,   //14
                0.0f, 0.0f, 1.0f,    //15

                1.0f, 1.0f, 1.0f,   //16
                0.0f, 0.0f, 1.0f,    //17
                0.0f, 1.0f, 1.0f,    //18
                1.0f, 1.0f, 1.0f,   //19

                1.0f, 1.0f, 1.0f,    //20
                1.0f, 1.0f, 1.0f,   //21
                0.5f, 0.5f, 0.5f,    //22
                0.0f, 1.0f, 1.0f    //23
        };

		//Solicitamos a criação de um buffer na OpenGL, onde esse array será guardado
		var colors = glGenBuffers();
		//Informamos a OpenGL que iremos trabalhar com esse buffer
		glBindBuffer(GL_ARRAY_BUFFER, colors);

		//Damos o comando para carregar esses dados na placa de vídeo
		//o parametro GL_STATIC_DRAW indica que não mexeremos mais nos valores desses dados em nossa aplicação
		glBufferData(GL_ARRAY_BUFFER, colorData, GL_STATIC_DRAW);

		//Procuramos o identificador do atributo de posição
		var aColor = glGetAttribLocation(shader, "aColor");

		//Chamamos uma função que associa as duas.
		glVertexAttribPointer(aColor, 3, GL_FLOAT, false, 0, 0);

		//Informamos a OpenGL que iremos trabalhar com essa variável
		glEnableVertexAttribArray(aColor);


		//Indices
		//Criação do buffer de posições
		//------------------------------
		//Criamos um array no java com as cores.
		var indexData = new int[] {
				0, 1, 2,
				0, 3, 1,
				10, 17, 14,
                10, 14, 15,
                7, 5, 4,
                7, 6, 5,
                20, 19, 16,
                20, 21, 19,
                12, 18, 23,
                12, 8, 18,
                11, 13, 22,
                11, 22, 9
		};

		var indices = glGenBuffers();
		//Informamos a OpenGL que iremos trabalhar com esse buffer
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indices);

		//Damos o comando para carregar esses dados na placa de vídeo
		//o parametro GL_STATIC_DRAW indica que não mexeremos mais nos valores desses dados em nossa aplicação
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexData, GL_STATIC_DRAW);




		//Como já finalizamos a carga, informamos a OpenGL que não estamos mais usando esse buffer.
		glBindBuffer(GL_ARRAY_BUFFER, 0);

		//Finalizamos o nosso VAO, portanto, informamos a OpenGL que não iremos mais trabalhar com ele
		glBindVertexArray(0);
	}

	@Override
	public void update(float secs) {
		//Testa se a tecla ESC foi pressionada
		if (keys.isPressed(GLFW_KEY_ESCAPE)) {
			//Fecha a janela, caso tenha sido
			glfwSetWindowShouldClose(glfwGetCurrentContext(), true);
			return;
		}

		if(keys.isDown(GLFW_KEY_D)){
		    angleY += Math.toRadians(100) * secs;
        }
		else if(keys.isDown(GLFW_KEY_A)){
            angleY -= Math.toRadians(100) * secs;
        }

        if(keys.isDown(GLFW_KEY_W)){
            angleX += Math.toRadians(100) * secs;
        }
        else if(keys.isDown(GLFW_KEY_S)){
            angleX -= Math.toRadians(100) * secs;
        }

        //angle += Math.toRadians(100) * secs;
	}

	@Override
	public void draw() {
		//Solicita a limpeza da tela
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
		glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);

		//Precisamos dizer qual VAO iremos desenhar
		glBindVertexArray(vao);

		//E qual shader program irá ser usado durante o desenho
		glUseProgram(shader);

		try(MemoryStack stack = MemoryStack.stackPush()){
			var transform = new Matrix4f()
					.rotateY(angleY)
                    .rotateX(angleX)
					.get(stack.mallocFloat(16));
			var uWorld = glGetUniformLocation(shader, "uWorld");
			glUniformMatrix4fv(uWorld, false, transform);
		}

		//Comandamos o desenho de 3 vértices
		glDrawElements(GL_TRIANGLES, 36, GL_UNSIGNED_INT, 0);
		//Faxina
		glBindVertexArray(0);
		glUseProgram(0);
	}

	@Override
	public void deinit() {
	}

	public static void main(String[] args) {
		new Window(new RotatingTriangle()).show();
	}
}